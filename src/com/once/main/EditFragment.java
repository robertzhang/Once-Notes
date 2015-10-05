package com.once.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.once.greendao.Note;
import com.once.greendao.utils.NoteUtils;
import com.once.utils.LogUtils;
import com.once.utils.StringUtils;
import com.once.view.sweetalertdialog.SweetAlertDialog;

public class EditFragment extends Fragment implements OnClickListener{

	private RelativeLayout note_item_layout_ib_left;
	private ImageButton note_item_layout_ib_right;
	private ImageButton note_item_layout_ib_pic;
	private ImageView note_item_layout_ib_delete;
	private TextView note_item_layout_updatetime;
	private TextView note_item_layout_tv;
	private EditText note_item_layout_et;
	private AnimationDrawable animDrawable;
	private boolean isNew = true;
	public Note editnote;
	private static Context mContext;
	
	public static final int fixedLength = 150;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		if(null != getArguments()){
			isNew = false;
			editnote = NoteUtils.getInstance(getActivity()).loadById(getArguments().getLong("Id"));
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return initView(inflater, container);
	}
	

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		StringUtils.getInstance().hideSoftInput(getActivity(), note_item_layout_et);
	}

	public View initView(LayoutInflater inflater,  ViewGroup container){
		View root = inflater.inflate(R.layout.note_item_layout, container, false);
		
		note_item_layout_ib_left = (RelativeLayout)root.findViewById(R.id.note_item_layout_ib_left);
		note_item_layout_ib_right = (ImageButton)root.findViewById(R.id.note_item_layout_ib_right);
		note_item_layout_ib_pic = (ImageButton)root.findViewById(R.id.note_item_layout_ib_pic);
		note_item_layout_ib_delete = (ImageView)root.findViewById(R.id.note_item_layout_ib_delete);
		note_item_layout_ib_left.setOnClickListener(this);
		note_item_layout_ib_right.setOnClickListener(this);
		note_item_layout_ib_delete.setOnClickListener(this);
		note_item_layout_ib_pic.setOnClickListener(this);
		
		note_item_layout_updatetime = (TextView)root.findViewById(R.id.note_item_layout_updatetime);
		note_item_layout_tv = (TextView)root.findViewById(R.id.note_item_layout_tv);
		
		note_item_layout_et = (EditText)root.findViewById(R.id.note_item_layout_et);
		note_item_layout_et.addTextChangedListener(tw);
		
		if(!isNew){
			note_item_layout_updatetime.setText(StringUtils.Date2String(editnote.getNoteupdateDate()));
			note_item_layout_et.setText(editnote.getNotecontent());
		}else{
			note_item_layout_updatetime.setText(StringUtils.Date2String(new Date()));
		}
		initImage();//test----
		
		return root;
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		
	}



	/**
	 * EditText监听器，用于监听输入的变化
	 */
	TextWatcher tw = new TextWatcher() {
		private CharSequence temp;
		private int start;
		private int end;
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			temp = arg0;
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			LogUtils.LogE("----------"+note_item_layout_et.getLineCount());
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			start = note_item_layout_et.getSelectionStart();
			end = note_item_layout_et.getSelectionEnd();
			
			note_item_layout_tv.setText(temp.length()+"/"+fixedLength);
//			if(temp.length() > fixedLength){
//				Toast.makeText(getActivity(), "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
//				s.delete(start-1, end);  
//				int tempSelection = start;  
//			}
		}
	};

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.note_item_layout_ib_left:
			LogUtils.LogE("left--");
			//如果返回的时候内容为空，则删除当前Note
			if(null != editnote && 0 == note_item_layout_et.getText().length()){
				NoteUtils.getInstance(getActivity()).deleteByEntity(editnote);
			}
			if(null == ((MainActivity)getActivity()).mainFragment){
				((MainActivity)getActivity()).mainFragment = new MainFragment();
			}
			((MainActivity)getActivity()).changeFragment(((MainActivity)getActivity()).mainFragment);
			break;
		case R.id.note_item_layout_ib_right:
			LogUtils.LogE("right--");
			dialog();
			break;
		case R.id.note_item_layout_ib_delete:
			LogUtils.LogE("delete--");
			startAnim(R.anim.delete_anim_list_open);
			animDrawable.stop();
			startAnim(R.anim.delate_anim_list_close);
			break;
		case R.id.note_item_layout_ib_pic:
			// 插入图片操作
			String[] strTag = {"拍照", "从相册中获取"}; 
			new AlertDialog.Builder(getActivity()).setTitle("添加图片").setItems(strTag, 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						try {
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							File out = new File(Environment.getExternalStorageDirectory(),"camera.jpg");
							Uri uri = Uri.fromFile(out);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
							startActivityForResult(intent, 0);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						/* 取得相片后返回本画面 */
						startActivityForResult(intent, 2);
					}
				}
			}).show();
			break;
		}
	}
	
	private void startAnim(int resid){
		note_item_layout_ib_delete.setBackgroundResource(resid);
		animDrawable = (AnimationDrawable)note_item_layout_ib_delete.getDrawable();
		if(null != animDrawable && !animDrawable.isRunning()){
			animDrawable.start();
		}
	}
	
	
	//保存成功的dialog提示
	public void dialog(){
		final String noteString = note_item_layout_et.getText().toString();
		String str;
		if(null != noteString && !"".equals(noteString)){
			str = isNew ? "新增笔记成功":"保存修改内容成功";
			new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
			.setTitleText(str)
			.setConfirmText("确定")
			.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
				public void onClick(SweetAlertDialog sweetAlertDialog) {
					// TODO Auto-generated method stub
					if(isNew){
						editnote = new Note();
						editnote.setNotecreateDate(new Date());
						isNew = false;
					}
					editnote.setNotecontent(note_item_layout_et.getText().toString());
					editnote.setNoteupdateDate(new Date());
					NoteUtils.getInstance(getActivity()).save(editnote);

					sweetAlertDialog.cancel();
				}
			})
			.showCancelButton(false)
			.show();
		}else{
			new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
			.setTitleText("操作错误")
			.setContentText("内容不可以为空！")
			.setConfirmText("确定")
			.show();
		}
	}
	
	
	// ------------ 插入图片的操作 ----------
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode==0) {
				File file = new File(Environment.getExternalStorageDirectory().toString(),"camera.jpg");
				Uri uri = Uri.fromFile(file);
				//Uri uri =  data.getData();
				startPhotoZoom(uri);
				return;
			}
			if (requestCode==2) {
				Uri uri = data.getData();
				startPhotoZoom(uri);
				return;
			}else if(requestCode == 3) 
				LogUtils.LogE("requestCode == 3");
			if(data != null){
				LogUtils.LogE("data!=null");
				setPicToView(data);
			}
		} catch (Exception e) {
		}
	}
	
	/**
     * 裁剪图片方法实现
     * 
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 设置裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 3);
    }

    /**
     * 设置头像图片
     * @param picdata
     */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			String uri = savaPhotoToLocal(photo);
			insertIntoEditText(getBitmapMime(photo, uri));
//			Drawable drawable = new BitmapDrawable(photo);
//			add_contacts_iv.setImageDrawable(drawable);
//			avatarIcon = photo;
		}
	}
	
	
	public static void initFilePath() {
//		File dir = new File("/data/data/"+mContext.getPackageName()+"/files/Once/temp_pic/");
		File dir = new File(mContext.getFilesDir().getPath()+"/Once/temp_pic/");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		
	}
	
	/**
	 * 保存图片，返回图片的名称
	 * @param btp
	 * @return
	 */
	public static String savaPhotoToLocal(Bitmap btp){
	    // 如果文件夹不存在则创建文件夹，并将bitmap图像文件保存
//	    File rootdir = Environment.getExternalStorageDirectory();
		initFilePath();
	    File rootdir = mContext.getFilesDir();
	    String imagerDir = rootdir.getPath() + "/Once/temp_pic/";
//	    File dirpath = createDir(imagerDir);
	    String filename =  System.currentTimeMillis() + ".jpg";
	    File tempFile = new File(imagerDir, filename);
	    if (!tempFile.exists()) {
	    	try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    String filePath = tempFile.getAbsolutePath();
	    FileOutputStream fileOut = null;
	    try {
	      // 将bitmap转为jpg文件保存
	      fileOut = new FileOutputStream(tempFile);
	      btp.compress(CompressFormat.JPEG, 80, fileOut);
	    } catch (FileNotFoundException e) {
	       e.printStackTrace();
	    } 
	    String uri = filename;
	    LogUtils.LogE("----save----"+filePath);
	    return uri;
	}
	
	// 获取Uri
	private Uri getUri(String str) {
		File out = new File(mContext.getFilesDir()
				.getAbsolutePath(), "/Once/temp_pic/"+str);
		Uri uri = Uri.fromFile(out);
		LogUtils.LogE("getUri ----- "+ uri.getPath());
		return uri;
	}
	
	private void initImage() {
		 Editable et = note_item_layout_et.getText();
//		 et.
		 String str = et.toString();
		 Pattern p = Pattern.compile("\\[\\$\\d{13}\\.jpg\\$\\]"); 
		 Matcher m = p.matcher(str);
		 Uri uri;
		 int num = 0;
		 while (m.find()) {
			 String content = m.group();
			 int start = m.start();
			 LogUtils.LogE(content);
			 uri = getUri(content.substring(2, content.length()-2));
			 Bitmap bmp = getBitmap(uri);
			 SpannableString ss = getBitmapMime(bmp,content.substring(2, content.length()-2));//2是起始位置，13名称长度，加1是因为最后一位不读
//			 insertIntoEditText(ss);
//			 et.replace(arg0, arg1, ss);
			 int len = content.length()-ss.length();
			 LogUtils.LogE("init image ----"+(start-num*len)+ ","+content.length()+","+(ss.length()-1));
			 et.replace(start-num*len, start+content.length()-num*len, ss);
			 note_item_layout_et.setText(et);
			 num++;
//			 et.insert(arg0, arg1)
		 }
	}
	
	/**
	  * 通过路径获取系统图片
	  * @param uri
	  * @return
	  */
	 private Bitmap getBitmap(Uri uri) {
	  Bitmap pic = null;
	  BitmapFactory.Options op = new BitmapFactory.Options();
	  op.inJustDecodeBounds = true;
	  Display display = getActivity().getWindowManager().getDefaultDisplay();
	  int dw = display.getWidth();
	  int dh = display.getHeight();
	  pic = BitmapFactory.decodeFile(uri.getPath(), op);
	  int wRatio = (int) Math.ceil(op.outWidth / (float) dw);
	  int hRatio = (int) Math.ceil(op.outHeight / (float) dh);
	  if (wRatio > 1 && hRatio > 1) {
	   op.inSampleSize = wRatio + hRatio;
	  }
	  op.inJustDecodeBounds = false;
	  pic = BitmapFactory.decodeFile(uri.getPath(), op);
	  return pic;
	 }

	 /**
	  * 图片转成SpannableString加到EditText中
	  * 
	  * @param pic
	  * @param uri
	  * @return
	  */
	 private SpannableString getBitmapMime(Bitmap pic, String name) {
	  int imgWidth = pic.getWidth();
	  int imgHeight = pic.getHeight();
	  
	  WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
	  int screenWidth = wm.getDefaultDisplay().getWidth() - 130;
//	  int screenWidth = note_item_layout_et.getWidth();
//	  int screenHeight = wm.getDefaultDisplay().getHeight();
	  float scalew = (float) (screenWidth-30) / imgWidth;
	  float scaleh = (float) screenWidth / imgHeight;
	  Matrix mx = new Matrix();
	  mx.setScale(scalew, scaleh);
	  pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
//	  String smile = uri.getPath();
	  
	  SpannableString ss = new SpannableString("[$"+name+"$]");
	  ImageSpan span = new ImageSpan(getActivity(), pic);
	  ss.setSpan(span, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	  return ss;
	 }

	 /**
	  * 插入图片到EditText
	  */
	 private void insertIntoEditText(SpannableString ss) {
	  Editable et = note_item_layout_et.getText();// 先获取Edittext中的内容
	  int start = note_item_layout_et.getSelectionStart();
	  et.insert(start, ss);// 设置ss要添加的位置
	  note_item_layout_et.setText(et);// 把et添加到Edittext中
	  note_item_layout_et.setSelection(start + ss.length());// 设置Edittext中光标在最后面显示
	 }

}
