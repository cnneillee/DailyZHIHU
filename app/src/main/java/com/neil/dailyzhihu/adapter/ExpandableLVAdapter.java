package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.neil.dailyzhihu.R;

import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class ExpandableLVAdapter extends BaseExpandableListAdapter {
    private List<MyGroup> mListList;
    private LayoutInflater inflater;

    public ExpandableLVAdapter(List<MyGroup> listList, Context context) {
        mListList = listList;
        this.inflater = LayoutInflater.from(context);
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
    public Object getChild(int groupPosition, int childPosition) {
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置第一级月份
        viewHolder.time.setText(mListList.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        Child entity = (Child) getChild(groupPosition, childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_lv_story_universal, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
        }
        //设置第二级时间和事件名称
        viewHolder.title.setText(entity.getTitle());
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupViewHolder {
        TextView time;
    }

    private class ChildViewHolder {
        TextView title;
    }
}
