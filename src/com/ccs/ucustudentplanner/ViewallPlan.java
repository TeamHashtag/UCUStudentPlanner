package com.ccs.ucustudentplanner;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewallPlan extends Activity {

	DbAdapter dbcon = new DbAdapter(this);

	ListView myList;

	ArrayList<String> arraylist = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_plan);

		myList = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arraylist);
		myList.setAdapter(adapter);

		dbcon.open();
		populatelist();
		dbcon.close();
		myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Toast.makeText(ViewallPlan.this,
						myList.getItemAtPosition(position).toString(),
						Toast.LENGTH_LONG).show();
				return false;

			}
		});

	}

	private void populatelist() {

		Cursor showAll = dbcon.method_showAllRecords();
		adapter.clear();
		adapter.notifyDataSetChanged();
		if (showAll.moveToFirst()) {
			do {
				adapter.add("No.: " + showAll.getString(0).toString() + "\n"
						+ "Plan: \n" + showAll.getString(2).toString() + "\n"
						+ "Plan Date: " + showAll.getString(1).toString()
						+ "\n" + "Subject: " + showAll.getString(3).toString()
						+ "\n" + "Title: " + showAll.getString(4).toString()
						+ "\n" + "Time: " + showAll.getString(5).toString());
			} while (showAll.moveToNext());
		}
	}
}