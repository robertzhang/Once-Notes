package com.once.main;

import java.util.ArrayList;
import java.util.List;

import com.once.adpter.NotesAdpter;
import com.once.greendao.Note;
import com.once.greendao.utils.NoteUtils;
import com.once.utils.LogUtils;
import com.once.view.sweetalertdialog.SweetAlertDialog;
import com.once.view.swipelistview.SwipeListView;
import com.once.view.swipelistview.SwipeListViewListener;
import com.once.view.swipelistview.SwipeListViewTouchListener;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainFragment extends Fragment implements OnClickListener{

	private SwipeListView lv;
	private FrameLayout note_layout_ib_right;
	private ImageButton note_layout_ib_left;
	private ImageView note_layout_ib_delete;
	
	private NotesAdpter notesAdpter;
	private List<Note> data;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//测试用
		data = new ArrayList<Note>();

		for(Note note:NoteUtils.getInstance(getActivity()).loadAll()){
			data.add(note);
		}
		LogUtils.LogE("onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return initView(inflater, container);
	}

	public View initView(LayoutInflater inflater,  ViewGroup container){
		View root = inflater.inflate(R.layout.notes_layout, container, false);

		note_layout_ib_right = (FrameLayout)root.findViewById(R.id.note_layout_ib_right);
		note_layout_ib_left  = (ImageButton)root.findViewById(R.id.note_layout_ib_left);
		note_layout_ib_right.setOnClickListener(this);
		note_layout_ib_left.setOnClickListener(this);
		
		note_layout_ib_delete = (ImageView)root.findViewById(R.id.note_layout_ib_delete);
		
		lv = (SwipeListView)root.findViewById(R.id.notes_list);
		
		lv.setSwipeListViewListener(new SwipeListViewListener() {

			@Override
			public void onStartOpen(int position, int action, boolean right) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onStartOpen");
			}

			@Override
			public void onStartClose(int position, boolean right) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onStartClose");
			}

			@Override
			public void onOpened(int position, boolean toRight) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onOpened");
				
			}

			@Override
			public void onMove(int position, float x) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onMove");
			}

			@Override
			public void onListChanged() {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onListChanged");
//				stopAnim();
			}

			@Override
			public void onLastListItem() {
				// TODO Auto-generated method stub
				
				LogUtils.LogE( "onLastListItem");
			}

			@Override
			public void onFirstListItem() {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onFirstListItem");
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onDismiss");
			}

			@Override
			public void onClosed(int position, boolean fromRight) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onClosed");
//				startAnim(R.anim.delate_anim_list_close);
			}

			@Override
			public void onClickFrontView(int position) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onClickFrontView");
				Bundle bundle = new Bundle();
				bundle.putLong("Id", data.get(position).getId());
				Fragment fragment = new EditFragment();
				fragment.setArguments(bundle);
				((MainActivity)getActivity()).changeFragment(fragment);
			}

			@Override
			public void onClickBackView(int position) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onClickBackView----"+position+"----"+data.get(position).getId());
				dialog(position);
				
			}

			@Override
			public void onChoiceStarted() {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onChoiceStarted");
			}

			@Override
			public void onChoiceEnded() {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onChoiceEnded");
			}

			@Override
			public void onChoiceChanged(int position, boolean selected) {
				// TODO Auto-generated method stub
				LogUtils.LogE( "onChoiceChanged");
			}

			@Override
			public int onChangeSwipeMode(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		
		notesAdpter = new NotesAdpter(getActivity(), R.layout.note_item, data, lv);
		lv.setAdapter(notesAdpter);
		
		return root;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.note_layout_ib_left:
			LogUtils.LogE("click to left");
			break;
		case R.id.note_layout_ib_right:
			LogUtils.LogE("click to right");
			((MainActivity)getActivity()).changeFragment(new EditFragment());
			break;
		default:
			break;
		}
	}

	//删除按钮的动画
	public AnimationDrawable animDrawable = null;
	private void startAnim(int resid){
		note_layout_ib_delete.setImageResource(resid);
		animDrawable = (AnimationDrawable)note_layout_ib_delete.getDrawable();
		if(null != animDrawable && !animDrawable.isRunning()){
			animDrawable.start();
		}
	}
	
	private void stopAnim(){
		if(null != animDrawable && animDrawable.isRunning())
			animDrawable.stop();
	}
	
	
	public void dialog(final int position){
		new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
		.setTitleText("你确定要删除?")
		.setContentText("删除后将无法恢复！")
		.setCancelText("取消")
		.setConfirmText("确定")
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				// TODO Auto-generated method stub
				LogUtils.LogE(position +"");
				NoteUtils.getInstance(getActivity()).deleteByEntity(data.get(position));
				data.remove(position);
				notesAdpter.notifyDataSetChanged();
				startAnim(R.anim.delete_anim_list_open);
				sweetAlertDialog.cancel();
				stopAnim();
				startAnim(R.anim.delate_anim_list_close);
//				sweetAlertDialog
//                .setTitleText("已删除!")
//                .setContentText("已成功移除记录！")
//                .setConfirmText("确定")
//                .setConfirmClickListener(null)
//                .showCancelButton(false)
//                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
			}
		})
		.showCancelButton(true)
		.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				
				sDialog.cancel();
			}
		}).show();
		notesAdpter.mSwipeListView.closeAnimate(position);
	}
	
	//获取View在listview中的位置
	public View getViewByPosition(int pos, SwipeListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition ) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}
	
	public int[] getViewLocation(View view){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}

}
