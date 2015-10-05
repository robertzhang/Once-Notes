package com.once.greendao.utils;

import android.content.Context;
import com.once.greendao.Note;
import com.once.greendao.NoteDao;

public class NoteUtils extends GreenDaoUtilsAbs<Note, NoteDao> {
	
	private static NoteUtils mInstance = null;
	
	public static NoteUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new NoteUtils(context);
		}
		return mInstance;
	}
	
	public NoteUtils(Context context){
		setDao(context);
	}

	@Override
	protected void setDao(Context context) {
		// TODO Auto-generated method stub
		dao = BaseApplication.getDaoSession(context).getNoteDao();
	}

}
