package com.ykapps.chkbox;

public class getsetinfo {
    
    //private variables
    int id;
    String note;
    boolean status;
     
    // Empty constructor
    public getsetinfo(){
         
    }
    // constructor
    public getsetinfo(int id,String note,boolean status){
        this.id = id;
        this.note = note;
        this.status=status;
    }
   
     
    // constructor
    public getsetinfo(String note,boolean status){
        this.note = note;
        this.status=status;
    }
    // getting ID
    public int getID(){
        return this.id;
    }
     
    // setting id
    public void setID(int id){
        this.id = id;
    }
     
     
    // getting phone number
    public String getnote(){
        return this.note;
    }
     
    // setting phone number
    public void setnote(String item){
        this.note = item;
    }
    
    // getting name
    public boolean getState(){
        return this.status;
    }
     
    // setting name
    public void setState(boolean state){
        this.status = state;
    }
}
