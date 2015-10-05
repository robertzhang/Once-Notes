package com.once.main;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.once.greendao.Note;
import com.once.greendao.Tag;
import com.once.greendao.utils.GreenDaoUtils;
import com.once.greendao.utils.GreenDaoUtilsAbs;
import com.once.greendao.utils.NoteUtils;
import com.once.greendao.utils.TagUtils;

import de.greenrobot.dao.AbstractDao;

public class Test extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e("zhangchao", "1");
//		Note note = new Note();
//		note.setId(Long.getLong("1"));
//		note.setTitle("title-1");
//		note.setNotecontent("content-1");
//		NoteUtils.getInstance(this).save(note);
		
//		Tag tag = new Tag();
//		tag.setTagcontent("tag-1");
//		TagUtils tu = new TagUtils(this);
//		tu.save(tag);
//		Log.e("zhangchao", "2");
//		Log.e("zhangchao", NoteUtils.getInstance(this).loadAll().size()+"");
//		Log.e("zhangchao", tu.loadAll().size()+"");
		
	}

}
