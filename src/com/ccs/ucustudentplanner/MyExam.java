package com.ccs.ucustudentplanner;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyExam extends Fragment {
	ImageView go;

	public MyExam() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_my_exam, container,
				false);
		go = (ImageView) rootView.findViewById(R.id.gol);
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					Intent golog = new Intent();
					startActivity(golog);
				} catch (ActivityNotFoundException activityException) {

				}

			}
		});
		return rootView;
	}
}
