package com.ykapps.chkbox;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "todolist";
 
    // Contacts table name
    private static final String TABLE_NAME ="list";
    
    
    //  Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOTE = "note";
    private static final String KEY_STATE = "state";

    
    
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +KEY_NOTE + " TEXT,"
                + KEY_STATE + " TEXT" + ")";
        
        
        db.execSQL(CREATE_CONTACTS_TABLE);
               
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new value
    void addData(getsetinfo info) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, info.getnote());
        values.put(KEY_STATE, info.getState()); 
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
    
 
 
    // Getting single item //getContact
    /*getsetinfo getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
        		KEY_NOTE, KEY_STATE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        getsetinfo contact = new getsetinfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3) );
        // return contact
        return contact;
    }*/
   
    // Getting All Contacts
    public ArrayList<getsetinfo> getAllContacts() {
    	ArrayList<getsetinfo> contactList = new ArrayList<getsetinfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ KEY_ID +" DESC";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	//home.ArrayofName.clear();
            do {
            	boolean b;
                getsetinfo contact = new getsetinfo();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setnote(cursor.getString(1));
                if(cursor.getString(2).equals("0")){
                	b=false;
                }
                else{
                	b=true;
                }
                contact.setState(b);
                
                //String name = cursor.getString(2);
                //home.ArrayofName.add(name);
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(getsetinfo info) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_STATE, info.getState());
        values.put(KEY_NOTE, info.getnote());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(info.getID()) });
    }
    
 // Updating single contact
    public int updaterow(String id,String note,String state) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note);
        values.put(KEY_STATE, state);
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { id });
    }
 
    // Deleting single contact
    public void deleteContact(getsetinfo contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 
    public void deleteRow(int rowId){
    	SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, KEY_ID + "="+rowId,null);
        }catch(Exception e){
        }
    }
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}
