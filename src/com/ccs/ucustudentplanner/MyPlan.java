package com.ccs.ucustudentplanner;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPlan extends Fragment {

	public MyPlan() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_my_plan, container,
				false);
		ImageView a = (ImageView) rootView.findViewById(R.id.imgplan);
		a.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {

				Go();
				
			}
			
			
			
		});
		return rootView;
	}

	public void Go() {
		Intent i = new Intent(getActivity(), Addplan.class);
		startActivity(i);
	}
}