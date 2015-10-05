package com.once.greendao.utils;

import com.once.greendao.DaoMaster;
import com.once.greendao.DaoSession;
import com.once.greendao.DaoMaster.DevOpenHelper;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application{

	//定义使用到的参数
	
	private static BaseApplication mInstance;
	
	private static DaoMaster daoMaster;

	private static DaoSession daoSession;

	private static String DB_NAME = "note_db";
	
	public void onCreate(){
		super.onCreate();
		if(null == mInstance){
			mInstance = this;
		}
	}

	public static DaoMaster getDaoMaster(Context context){
		if(null == daoMaster){
			DevOpenHelper devHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
			daoMaster = new DaoMaster(devHelper.getWritableDatabase());
		}
		return daoMaster;
	}
	
	public static DaoSession getDaoSession(Context context){
		if(null == daoSession){
			if(null == daoMaster){
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}


}
