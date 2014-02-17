package com.ykapps.chkbox;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	
	final DatabaseHandler db = new DatabaseHandler(this);
	Button b;
	EditText t;
	CheckBox c;
	
	
	MyCustomAdapter dataAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		c=(CheckBox)findViewById(R.id.checkBox1);
		t=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.addData(new getsetinfo(t.getText().toString(),false));
				Intent intent = getIntent();
			    finish();
			    startActivity(intent);
				
			}
		});
		
		// Generate list View from ArrayList
		displayListView();
		//save(stateList.get(2).toString());
		//checkButtonClick();
	

	}

	private void displayListView() {
		ArrayList<getsetinfo> stateList = new ArrayList<getsetinfo>();
		stateList=db.getAllContacts();
		//Toast.makeText(MainActivity.this, stateList.get(1).toString(), Toast.LENGTH_LONG).show();
		// Array list of countries
		

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.row_view, stateList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		
listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					final int position,long arg3) {
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				// Vibrate for 500 milliseconds
				v.vibrate(50);
				// TODO Auto-generated method stub
				AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
				b.setIcon(android.R.drawable.ic_dialog_alert);
				b.setMessage("Choose Delete or Edit");
				b.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
						  
						 /* Toast.makeText(getApplicationContext(),
                                  "Deleted", Toast.LENGTH_LONG)
                                  .show();*/
						  TextView dbid = (TextView) arg1.findViewById(R.id.code);
						  db.deleteRow(Integer.parseInt(dbid.getText().toString()));
						  //ma.notifyDataSetChanged();
						  Intent intent = getIntent();
						    finish();
						    startActivity(intent);
					  }
					});
				   b.show();
					return false;
				}
			});

		listView.setOnItemClickListener(new OnItemClickListener() {
			

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				//getsetinfo state = (getsetinfo) parent.getItemAtPosition(position);
				/*Toast.makeText(getApplicationContext(),
						"Clicked on : " + state.getName(), Toast.LENGTH_LONG)
						.show();*/
			}
		});
	}

	private class MyCustomAdapter extends ArrayAdapter<getsetinfo> {

		private ArrayList<getsetinfo> stateList;

		public MyCustomAdapter(Context context, int textViewResourceId,

		ArrayList<getsetinfo> stateList) {
			super(context, textViewResourceId, stateList);
			this.stateList = new ArrayList<getsetinfo>();
			this.stateList.addAll(stateList);
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = vi.inflate(R.layout.row_view, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkBox1);
				

				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						getsetinfo _state = (getsetinfo) cb.getTag();

						
					
					if(cb.isChecked()){
						
						db.updateContact(new getsetinfo(stateList.get(position).getID(),stateList.get(position).getnote().toString(), true));
					}
					else{
						
						db.updateContact(new getsetinfo(stateList.get(position).getID(),stateList.get(position).getnote().toString(),false));
						
					}

						_state.setState(cb.isChecked());
					}
				});

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			getsetinfo state = stateList.get(position);
			//holder.code.setText(stateList.get(position).getnote());
			holder.code.setText(state.getnote());
			//holder.name.setText(state.getnote());
			holder.name.setChecked(state.getState());

			holder.name.setTag(state);

			return convertView;
		}

	}


}