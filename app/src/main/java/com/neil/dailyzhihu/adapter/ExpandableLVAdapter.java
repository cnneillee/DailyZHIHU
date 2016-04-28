package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.StarRecord;
import com.neil.dailyzhihu.utils.db.FavoriteStory;

import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class ExpandableLVAdapter extends BaseExpandableListAdapter {
    private List<MyGroup> mListList;
    private LayoutInflater inflater;
    private Context mContext;

    public ExpandableLVAdapter(List<MyGroup> listList, Context context) {
        mListList = listList;
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mListList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mListList.get(groupPosition) != null)
            return mListList.get(groupPosition).getChildList().size();
        return -1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListList.get(groupPosition);
    }

    @Override
    public FavoriteStory getChild(int groupPosition, int childPosition) {
        return mListList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.one_status_item, null);
            viewHolder = new GroupViewHolder();
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        String time = DateUtils.getRelativeTimeSpanString(mContext, Long.valueOf(mListList.get(groupPosition).getName())).toString();
        int count = getChildrenCount(groupPosition);
        //设置第一级月份
        viewHolder.time.setText(time);
        viewHolder.count.setText(count + " 个收藏");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        FavoriteStory entity = getChild(groupPosition, childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.childitem_expandable_lv, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.author = (TextView) convertView.findViewById(R.id.tv_author);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.tv_descripsion);
        }
        //设置第二级时间和事件名称
        viewHolder.title.setText(entity.getTitle());
        viewHolder.author.setText(entity.getAuthor());
        viewHolder.desc.setText(entity.getDesc() + "");
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder {
        TextView time;
        TextView count;
    }

    private class ChildViewHolder {
        TextView author;
        TextView title;
        TextView desc;
    }

}
