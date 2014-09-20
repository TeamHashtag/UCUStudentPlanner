package com.ccs.ucustudentplanner;

import java.util.Calendar;

import android.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Addplan extends Activity {

	DbAdapter dbcon = new DbAdapter(this);

	private TextView Output;
	private Button changeDate;
	private int year;
	private int month;
	private int day;

	Button btnSave, btnSearch, btnDelete, btnEdit;
	EditText editComposeNotes, editSearch, notesid, editSubject, editTitle,
			editTime;
	int daySearch;

	int notes_id;
	String vdate, vmsg, vsubject, vtitle, vtime, vcontact;
	static final int DATE_PICKER_ID = 1111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addplan);

		Output = (TextView) findViewById(R.id.Output);
		changeDate = (Button) findViewById(R.id.changeDate);

		btnSave = (Button) findViewById(R.id.btnSave);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnEdit = (Button) findViewById(R.id.btnEdit);

		editComposeNotes = (EditText) findViewById(R.id.editComposeNotes);
		editSearch = (EditText) findViewById(R.id.editSearch);
		notesid = (EditText) findViewById(R.id.notes_id);

		editSubject = (EditText) findViewById(R.id.editSubject);
		editTitle = (EditText) findViewById(R.id.editTitle);
		editTime = (EditText) findViewById(R.id.editTime);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Output.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

		changeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// On button click show datepicker dialog
				showDialog(DATE_PICKER_ID);

			}

		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dbcon.open();

				long connectionsave = dbcon.method_compose(Output.getText()
						.toString(), editComposeNotes.getText().toString(),
						editSubject.getText().toString(), editTitle.getText()
								.toString(), editTime.getText().toString());

				if (connectionsave > 0) {
					Toast.makeText(getApplicationContext(), " Notes Saved ",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Your notes are not saved", Toast.LENGTH_LONG)
							.show();
				}

				dbcon.close();
			}

		});

		// SEARCH CODE
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dbcon.open();

				daySearch = Integer.parseInt(editSearch.getText().toString());
				Cursor searchid = dbcon.method_searchid(daySearch);

				if (searchid.moveToFirst()) {

					Integer reccount = searchid.getCount();

					Toast.makeText(getApplicationContext(),
							reccount.toString() + "Record(s) Found",
							Toast.LENGTH_SHORT).show();

					notesid.setText(searchid.getString(0).toString());
					editComposeNotes.setText(searchid.getString(2).toString());
					Output.setText(searchid.getString(1).toString());
					editSubject.setText(searchid.getString(3).toString());
					editTitle.setText(searchid.getString(4).toString());
					editTime.setText(searchid.getString(5).toString());

					editSearch.setText("");

				} else {

					Toast.makeText(getApplicationContext(), "Record NOT Found",
							Toast.LENGTH_SHORT).show();

				}

				dbcon.close();
			}

		});

		// DELETE CODE
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				daySearch = Integer.parseInt(notesid.getText().toString());
				dbcon.open();

				if (dbcon.method_delete(daySearch)) {
					Toast.makeText(getApplicationContext(), " Notes Deleted ",
							Toast.LENGTH_LONG).show();

					editComposeNotes.setText("");
					Output.setText("MM-DD-YYYY");

				} else {
					Toast.makeText(getApplicationContext(), "Deleting Failed!",
							Toast.LENGTH_LONG).show();

				}

				dbcon.close();

			}

		});

		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				daySearch = Integer.parseInt(notesid.getText().toString());
				vdate = Output.getText().toString();
				vmsg = editComposeNotes.getText().toString();
				vsubject = editSubject.getText().toString();
				vtitle = editTitle.getText().toString();
				vtime = editTime.getText().toString();
				vcontact = editContact.getText().toString();

				dbcon.open();

				if (dbcon.method_update(daySearch, vdate, vmsg, vsubject,
						vtitle, vtime)) {
					Toast.makeText(getApplicationContext(), "Record Updated!",
							Toast.LENGTH_LONG).show();

					Output.setText("MM-DD-YYYY");
					editComposeNotes.setText("");

				} else {
					Toast.makeText(getApplicationContext(), "Updating Failed!",
							Toast.LENGTH_LONG).show();
					Output.setText("MM-DD-YYYY");
					editComposeNotes.setText("");
				}
				dbcon.close();
			}

		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			Output.setText(new StringBuilder().append(month + 1).append("-")
					.append(day).append("-").append(year).append(" "));

		}
	};

}