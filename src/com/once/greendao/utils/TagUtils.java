package com.once.greendao.utils;

import com.once.greendao.Tag;
import com.once.greendao.TagDao;

import android.content.Context;

public class TagUtils extends GreenDaoUtilsAbs<Tag, TagDao> {

	private static TagUtils mInstance = null;

	public static TagUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new TagUtils(context);
		}
		return mInstance;
	}

	public TagUtils(Context context){
		setDao(context);
	}

	protected void setDao(Context context){
		dao = BaseApplication.getDaoSession(context).getTagDao();
	}
	
}
