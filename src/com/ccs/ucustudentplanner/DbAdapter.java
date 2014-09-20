package com.ccs.ucustudentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {

	// database
	static final int DATABASE_VERSION = 2;
	static final String DATABASE_NAME = "db_user";

	//
	static final String NOTES_ID = "notes_id";
	static final String NOTES_DATE = "notes_date";
	static final String NOTES_MSG = "notes_msg";
	static final String NOTES_SUBJECT = "notes_subject";
	static final String NOTES_TITLE = "notes_title";
	static final String NOTES_TIME = "notes_time";
	static final String NOTES_CONTACT = "notes_contact";

	// table
	static final String TABLE_NAME = "tb_user";
	static final String TABLE_NAME2 = "tb_notes";

	static final String DATABASE_CREATE2 = "CREATE TABLE " + TABLE_NAME2
			+ " ( " + NOTES_ID + " integer primary key autoincrement, "
			+ NOTES_DATE + " text not null ," + NOTES_MSG + " text not null ,"
			+ NOTES_SUBJECT + " text not null ," + NOTES_TITLE
			+ " text not null," + NOTES_TIME + " text not null )";

	// DBHELPER
	final Context context;

	DatabaseHelper DBHelper;

	SQLiteDatabase db;

	public DbAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// ON CREATE
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {

				db.execSQL(DATABASE_CREATE2);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ON UPGRADE (DROP DUPLICATE)
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

			onCreate(db);
		}
	}

	public DbAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long method_compose(String date, String message, String subject,
			String title, String time) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(NOTES_DATE, date);
		initialValues.put(NOTES_MSG, message);
		initialValues.put(NOTES_SUBJECT, subject);
		initialValues.put(NOTES_TITLE, title);
		initialValues.put(NOTES_TIME, time);
		return db.insert(TABLE_NAME2, null, initialValues);
	}

	public Cursor method_showAllRecords() {

		Cursor search_cursor = db.query(true, TABLE_NAME2, new String[] {
				NOTES_ID, NOTES_DATE, NOTES_MSG, NOTES_SUBJECT, NOTES_TITLE,
				NOTES_TIME }, null, null, null, null, null, null);
		return search_cursor;
	}

	// METHOD FOR UPDATE
	public boolean method_update(long nid, String ndate, String nmsg,
			String nsubject, String ntitle, String ntime) {
		ContentValues args = new ContentValues();
		args.put(NOTES_ID, nid);
		args.put(NOTES_DATE, ndate);
		args.put(NOTES_MSG, nmsg);
		args.put(NOTES_SUBJECT, nsubject);
		args.put(NOTES_TITLE, ntitle);
		args.put(NOTES_TIME, ntime);
		return db.update(TABLE_NAME2, args, NOTES_ID + " = " + nid + "", null) > 0;
	}

	// METHOD FOR DELETE
	public boolean method_delete(long nid) {
		return db.delete(TABLE_NAME2, NOTES_ID + " = " + nid + "", null) > 0;
	}

	// METHOD FOR THE SEARCH
	public Cursor method_searchid(long nid) {

		Cursor search_cursor = db.query(true, TABLE_NAME2, new String[] {
				NOTES_ID, NOTES_DATE, NOTES_MSG, NOTES_SUBJECT, NOTES_TITLE,
				NOTES_TIME }, NOTES_ID + " = " + nid + "", null, null, null,
				null, null);

		return search_cursor;
	};

}// end
