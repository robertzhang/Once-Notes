package com.once.main;

import com.once.utils.LogUtils;
import com.once.view.sweetalertdialog.SweetAlertDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.WindowManager;


public class MainActivity extends FragmentActivity{

	Fragment mainFragment = null;
	Fragment editFragment = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//控制键盘遮挡
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		setContentView(R.layout.main_layout);
	
		 if(savedInstanceState == null){
			 if(null == mainFragment)
				 mainFragment = new MainFragment();
			 changeFragment(mainFragment);
		 }
	          
		
	}
	
	/*
	 * 切换Fragment
	 */
	public void changeFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction()
								   .replace(R.id.main_fragment, fragment)
								   .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
								   .commit();
	}
	
}
