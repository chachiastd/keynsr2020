package com.riverplate.keyboardtheme.keybord;

import com.riverplate.keyboardtheme.R;
import com.riverplate.keyboardtheme.module.constants.ApplicationPrefs;
import com.riverplate.keyboardtheme.adapter.ListAdapter;
/*import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;*/
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThemesListActivity extends Activity implements OnItemClickListener{
	
	ApplicationPrefs applicationPrefs;
	private GridView listView_themes_list;
	private ListAdapter listAdapter;
	//private StartAppAd startAppAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 //StartAppSDK.init(ThemesListActivity.this, AppConstants.DeveloperID, AppConstants.ApplicationID, true);
		setContentView(R.layout.activity_themes_list);
		applicationPrefs = ApplicationPrefs.getInstance(this);
		
		 //startAppAd = new StartAppAd(getApplicationContext());
		
		initUI();
	}
	
	


	private void initUI() {
		// TODO Auto-generated method stub
		
		listAdapter = new ListAdapter(getApplicationContext());
		listView_themes_list = (GridView) findViewById(R.id.listView_themes_list);	
		listView_themes_list.setAdapter(listAdapter);
		listView_themes_list.setOnItemClickListener(this);
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		applicationPrefs.setThemesId(position+1);
		finish();

		 /*startAppAd.showAd(); // show the ad
		 startAppAd.loadAd();*/ // load the next ad
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	   // startAppAd.onResume();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	   // startAppAd.onPause();
	}
	@Override
	public void onBackPressed() {
	/*startAppAd.onBackPressed();
	 startAppAd.showAd(); // show the ad
	 startAppAd.loadAd();*/ // load the next ad
	super.onBackPressed();
	}
}