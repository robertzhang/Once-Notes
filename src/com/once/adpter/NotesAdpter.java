package com.once.adpter;

import java.util.List;

import com.once.greendao.Note;
import com.once.main.R;
import com.once.utils.StringUtils;
import com.once.view.swipelistview.SwipeListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class NotesAdpter extends ArrayAdapter<Note> {
        private LayoutInflater mInflater ;
        private List<Note> data ;
        public SwipeListView mSwipeListView ; 
        
        public final int fixedLength = 50;
        
        public NotesAdpter(Context context, int textViewResourceId,List<Note> objects, SwipeListView mSwipeListView) {
                super(context, textViewResourceId, objects);
                this.data = objects ;
                this.mSwipeListView = mSwipeListView ;
                mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public Note getItem(int position) {
            return data.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public int getItemCount() {
            return data.size();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null ;
                if(convertView == null){
                        convertView = mInflater.inflate(R.layout.note_item, parent, false);
                        holder = new ViewHolder();
                        holder.note_item_content_tv = (TextView) convertView.findViewById(R.id.note_item_content_tv);
                        holder.note_item_date_tv = (TextView) convertView.findViewById(R.id.note_item_date_tv);
                        holder.note_item_delete_bt = (ImageButton) convertView.findViewById(R.id.note_item_delete_bt);
                        convertView.setTag(holder);
                }else{
                        holder = (ViewHolder) convertView.getTag();
                }

                Note item = getItem(position);
                if(item.getNotecontent().length() > fixedLength){
                	holder.note_item_content_tv.setText(item.getNotecontent().substring(0, fixedLength-1)+"...");
                }else{
                	holder.note_item_content_tv.setText(item.getNotecontent());
                }
                holder.note_item_date_tv.setText(StringUtils.Date2String(item.getNoteupdateDate()));
                return convertView;
        }
        class ViewHolder{
                TextView note_item_content_tv ;
                TextView note_item_date_tv;
                ImageButton note_item_delete_bt ;
        }
}

