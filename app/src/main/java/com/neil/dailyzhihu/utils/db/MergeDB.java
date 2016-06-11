package com.neil.dailyzhihu.utils.db;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.orignallayer.DetailStory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/5/7.
 */
public class MergeDB {
    private static MergeProgressListener sListener;

    public static void merge(Context context, MergeProgressListener listener) {
        sListener = listener;
        sListener.onStarted();
        Log.e("LOG_TAG", "1" + System.currentTimeMillis());
        mergeSimpleDB(context);
        Log.e("LOG_TAG", "2" + System.currentTimeMillis());
        mergeDetailStoryDB(context);
        sListener.onProgressing("merging done!");
        sListener.onCompleted();
        Log.e("LOG_TAG", "3" + System.currentTimeMillis());
    }

    private static void mergeSimpleDB(Context context) {
        sListener.onProgressing("1、begin to merge SimpleDB...");
        List<Integer> simpleStoryStoryIdList = DBFactory.getsIDBSimpleStoryTabledao(context).queryAllSimpleStoryId();
        sListener.onProgressing("merging simpleDB...");
        for (Integer i : simpleStoryStoryIdList) {
            if (isStoryIdValuable(i, context)) {
                continue;
            }
            DBFactory.getsIDBSimpleStoryTabledao(context).dropSimpleStory(i);
        }
        sListener.onProgressing("merging simpleDB done!");
    }

    private static void mergeDetailStoryDB(Context context) {
        List<Integer> cleanDetainStoryIdList = DBFactory.getIDBDetailStoryTabledao(context).queryAllDetailStoryId();
        for (int i = 0; i < cleanDetainStoryIdList.size(); i++) {
            if (DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(cleanDetainStoryIdList.get(i)) != null)
                continue;
            if (DBFactory.getIIDBStarRecordDetailStoryTabledao(context).queryStarRecordByStoryId(cleanDetainStoryIdList.get(i)) != null)
                continue;
            if (DBFactory.getsIDBShareRecordDetailStoryTabledao(context).queryShareRecordByStoryId(cleanDetainStoryIdList.get(i)) != null)
                continue;
            DBFactory.getIDBDetailStoryTabledao(context).dropDetailStory(cleanDetainStoryIdList.get(i));
        }
    }
    /*private static void mergeDetailStoryDB(Context context) {
        sListener.onProgressing("2、begin to merge DetailStoryDB...");
        List<Integer> cleanDetainStoryIdList = DBFactory.getIDBDetailStoryTabledao(context).queryAllDetailStoryId();
        sListener.onProgressing("merging DetailStoryDB...");
        List<Integer> simpleStoryIdList = DBFactory.getsIDBSimpleStoryTabledao(context).queryAllSimpleStoryId();
        sListener.onProgressing("merging DetailStoryDB from SimpleStoryDB...");
        cleanDetainStoryIdList = dealWithToList(simpleStoryIdList, cleanDetainStoryIdList);
        List<Integer> starRecordStoryId = DBFactory.getIIDBStarRecordDetailStoryTabledao(context).queryAllStarRecordStoryId();
        sListener.onProgressing("merging DetailStoryDB from StarRecordDB...");
        cleanDetainStoryIdList = dealWithToList(starRecordStoryId, cleanDetainStoryIdList);
        List<Integer> shareRecordStoryId = DBFactory.getsIDBShareRecordDetailStoryTabledao(context).queryAllShareRecordStoryId();
        sListener.onProgressing("merging DetailStoryDB from ShareRecordDB...");
        cleanDetainStoryIdList = dealWithToList(shareRecordStoryId, cleanDetainStoryIdList);
        for (int i = 0; i < cleanDetainStoryIdList.size(); i++) {
            DBFactory.getIDBDetailStoryTabledao(context).dropDetailStory(cleanDetainStoryIdList.get(i));
        }
        sListener.onProgressing("merging DetailStoryDB done!");
    }*/

    private static List<Integer> dealWithToList(List<Integer> standard, List<Integer> toMerge) {
        if (standard == null) return toMerge;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < standard.size(); i++) {
            for (int j = 0; j < toMerge.size(); j++) {
                if (isEqual(standard.get(i), toMerge.get(j))) {
                    continue;
                }
                result.add(j);
            }
        }
        return result;
    }


    private static boolean isEqual(Integer a, Integer b) {
        String aStr = String.valueOf(a);
        String bStr = String.valueOf(b);
        return aStr.equals(bStr);
    }

    public static boolean isStoryIdValuable(int storyId, Context context) {
        if (DBFactory.getsIDBSpecialSimpleStoryTabledao(context).querySimpleStoryById(storyId, 0) != null)
            return true;
        if (DBFactory.getsIDBSpecialSimpleStoryTabledao(context).querySimpleStoryById(storyId, 1) != null)
            return true;
        if (DBFactory.getsIDBSpecialSimpleStoryTabledao(context).querySimpleStoryById(storyId, 2) != null)
            return true;
        return false;
    }
}
