package com.neil.dailyzhihu.utils.cnt;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanBeforeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanHotStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.listener.OnCleanStoryListBeanListener;
import com.neil.dailyzhihu.bean.listener.OnStoryExtraLoadingListener;
import com.neil.dailyzhihu.bean.orignallayer.BeforeStoryListBean;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.bean.orignallayer.LatestStory;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.MyStringRequest;
import com.neil.dailyzhihu.utils.db.CleanLayer;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;
import com.neil.dailyzhihu.utils.db.catalog.a.MyDBHelper;
import com.neil.dailyzhihu.utils.network.NetworkInfo;
import com.neil.dailyzhihu.utils.network.NetworkListener;
import com.neil.dailyzhihu.utils.network.NetworkUtil;

import java.util.List;

public class UniversalContentLoaderTest implements ContentLoaderWrapper {
    private static RequestQueue sRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (sRequestQueue == null) {
            synchronized (UniversalContentLoaderTest.class) {
                if (sRequestQueue == null) {
                    sRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
                }
            }
        }
        return sRequestQueue;
    }

    @Override
    public void loadContent(String contentUrl) {
        loadContent(contentUrl, null);
    }

    @Override
    public void loadContent(String contentUrl, OnContentLoadingFinishedListener listener) {

    }

    public enum Flag {
        HOT, LATEST, BEFORE
    }

    public void loadCleanStoryListBean(final Context context, final String contentUrl, final OnCleanStoryListBeanListener listener, final Flag flag) {
        if (listener != null) listener.onStart();
        NetworkUtil.getNetworkInfo(new NetworkListener() {
            @Override
            public void onResult(NetworkInfo networkInfo) {
                if (networkInfo.isStatus()) {
                    networkrequest(context, contentUrl, listener, flag);
                } else {
                    dbrequest(context, listener, flag);
                }
                Toast.makeText(context, networkInfo.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkrequest(final Context context, String contentUrl, final OnCleanStoryListBeanListener listener, final Flag flag) {
        MyStringRequest stringRequest = new MyStringRequest(contentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int flagInt = -1;
                if (flag.equals(Flag.LATEST)) flagInt = 0;
                else if (flag.equals(Flag.HOT)) flagInt = 1;
                else if (flag.equals(Flag.BEFORE)) flagInt = 2;
                CleanStoryListBean cleanStoryListBean = null;
                if (listener != null) listener.onContentLoaded(response);
                if (flag.equals(Flag.LATEST)) {
                    LatestStory latestStory = GsonDecoder.getDecoder().decoding(response, LatestStory.class);
                    cleanStoryListBean = CleanDataHelper.cleanOrignalStory(latestStory);
                } else if (flag.equals(Flag.BEFORE)) {
                    BeforeStoryListBean beforeStoryListBean = GsonDecoder.getDecoder().decoding(response, BeforeStoryListBean.class);
                    cleanStoryListBean = CleanDataHelper.cleanOrignalStory(beforeStoryListBean);
                } else if (flag.equals(Flag.HOT)) {
                    HotStory hotStory = GsonDecoder.getDecoder().decoding(response, HotStory.class);
                    cleanStoryListBean = CleanDataHelper.cleanOrignalStory(hotStory);
                }
                if (listener != null) listener.onFinish(cleanStoryListBean);
                //存储当前，删除过去
                if (cleanStoryListBean != null && cleanStoryListBean.getSimpleStoryList() == null && cleanStoryListBean.getSimpleStoryList().size() > 0) {
                    DBFactory.getsIDBSpecialSimpleStoryTabledao(context).dropAllSimpleStory(flagInt);
                    DBFactory.getsIDBSpecialSimpleStoryTabledao(context).addSimpleStoryList(cleanStoryListBean.getSimpleStoryList(), flagInt);
                    extraLoadListStory(cleanStoryListBean.getSimpleStoryList(), context);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                if (listener != null) listener.onError(error.getMessage());
            }
        });
        getRequestQueue(context).add(stringRequest);
    }

    public void loadExtra(final Context context, final int storyId, final OnStoryExtraLoadingListener listener) {
        if (listener != null) listener.onStart();
        NetworkUtil.getNetworkInfo(new NetworkListener() {
            @Override
            public void onResult(NetworkInfo networkInfo) {
                if (networkInfo.isStatus()) {
                    extraLoad(storyId, context, listener);
                } else {
                    CleanStoryExtra cleanStoryExtra = DBFactory.getsIDBSimpleStoryTabledao(context).queryStoryExtraById(storyId);
                    if (listener != null && cleanStoryExtra != null)
                        listener.onFinish(cleanStoryExtra);
                }
                Toast.makeText(context, networkInfo.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static void extraLoadListStory(final List<SimpleStory> simpleStoryList, final Context context) {
        for (SimpleStory simpleStory : simpleStoryList)
            extraLoad(simpleStory.getStoryId(), context, null);
    }

    private static void extraLoadListId(final List<Integer> storyIdList, final Context context) {
        for (Integer i : storyIdList)
            extraLoad(i, context, null);
    }

    public static void extraLoad(final int storyId, final Context context, final OnStoryExtraLoadingListener listener) {
        MyStringRequest stringRequest = new MyStringRequest(Constant.EXTRA_HEAD + storyId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StoryExtra extra = GsonDecoder.getDecoder().decoding(response, StoryExtra.class);
                if (extra == null) return;
                CleanStoryExtra cleanStoryExtra = new CleanStoryExtra(extra.getLong_comments() + "", extra.getShort_comments() + "", extra.getPopularity() + "");
                if (listener != null) listener.onFinish(cleanStoryExtra);
                ContentValues cv = new ContentValues();
                cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_LONG_COMMENTS, extra.getLong_comments());
                cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_SHORT_COMMENTS, extra.getShort_comments());
                cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_POPULARITY, extra.getPopularity());
                DBFactory.getsIDBSimpleStoryTabledao(context).updateSimpleStory(storyId, cv);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        getRequestQueue(context).add(stringRequest);
    }

    public static void dbrequest(Context context, OnCleanStoryListBeanListener listener, Flag flag) {
        int flagInt = -1;
        if (flag.equals(Flag.LATEST)) flagInt = 0;
        else if (flag.equals(Flag.HOT)) flagInt = 1;
        else if (flag.equals(Flag.BEFORE)) flagInt = 2;
        else listener.onFinish(null);
        CleanStoryListBean cleanStoryListBean = new CleanStoryListBean();
        List<SimpleStory> simpleStoryList = DBFactory.getsIDBSpecialSimpleStoryTabledao(context).queryAllSimpleStory(flagInt);
        cleanStoryListBean.setSimpleStoryList(simpleStoryList);
        if (simpleStoryList != null && simpleStoryList.size() > 0)
            cleanStoryListBean.setDate(simpleStoryList.get(0).getDate());
        listener.onFinish(cleanStoryListBean);
    }

    @Override
    public void loadContent(Context context, String contentUrl, final OnContentLoadingFinishedListener listener) {
        StringRequest stringRequest = new StringRequest(contentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) listener.onFinish(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        getRequestQueue(context).add(stringRequest);
    }
}
