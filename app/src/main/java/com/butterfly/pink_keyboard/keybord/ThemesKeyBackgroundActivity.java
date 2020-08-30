package com.butterfly.pink_keyboard.keybord;

import com.butterfly.pink_keyboard.R;
import com.butterfly.pink_keyboard.module.constants.AppConstants;
import com.butterfly.pink_keyboard.module.constants.ApplicationPrefs;
import com.butterfly.pink_keyboard.adapter.ThemesKeyBackgroundAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ThemesKeyBackgroundActivity extends Activity implements OnItemClickListener{
	
	ApplicationPrefs applicationPrefs;
	private GridView listView_themes_list;
	private ThemesKeyBackgroundAdapter listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_themes_list);
		applicationPrefs = ApplicationPrefs.getInstance(this);
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		listAdapter = new ThemesKeyBackgroundAdapter(getApplicationContext());
		listView_themes_list = (GridView) findViewById(R.id.listView_themes_list);	
		listView_themes_list.setAdapter(listAdapter);
		listView_themes_list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub 
		
		applicationPrefs.setCustomeThemesKeyBackground(AppConstants.KEY_BACKGROUND_LIST[position]);
		 Intent intent=new Intent();  
         intent.putExtra("MESSAGE","done");  
         setResult(2,intent);  
         finish();//finishing activity  
	}

}
