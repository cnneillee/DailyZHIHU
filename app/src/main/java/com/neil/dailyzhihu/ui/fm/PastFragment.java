package com.neil.dailyzhihu.ui.fm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.DownloadedHighLightDecorator;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanBeforeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.orignallayer.BeforeStoryListBean;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.DateUtil;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.StoryDownloader;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class PastFragment extends Fragment implements AdapterView.OnItemClickListener, ObservableScrollViewCallbacks, View.OnClickListener {
    private static final String LOG_TAG = PastFragment.class.getSimpleName();

    @Bind(R.id.lv_before)
    ObservableListView mLvBefore;

    private Context mContext;
    private List<SimpleStory> mDatas;
    private int dbFlag = 2;
    private String pickdate;
    private DownloadedHighLightDecorator downloadedHighLightDecorator;

    private View header;
    private Button mBtnLoadSetting;
    private Button mBtnPickDate;
    private MaterialCalendarView mMCV;
    private TextView mTvDateDisplay;


    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mLvBefore.setOnItemClickListener(this);
//        mLvBefore.setScrollViewCallbacks(this);
        initViews();
        loadData();
    }

    private void initViews() {
        header = LayoutInflater.from(mContext).inflate(R.layout.fm_past_header, null);
        mBtnPickDate = (Button) header.findViewById(R.id.btn_pickDate);
        mBtnLoadSetting = (Button) header.findViewById(R.id.btn_loadsetting);
        mMCV = (MaterialCalendarView) header.findViewById(R.id.material_calendarview);
        mTvDateDisplay = (TextView) header.findViewById(R.id.tv_dateDisplay);
        initMaterialCalendarView(mMCV);
        mBtnPickDate.setOnClickListener(this);
        mBtnLoadSetting.setOnClickListener(this);
        mLvBefore.addHeaderView(header);
    }

    private void loadData() {
        if (TextUtils.isEmpty(pickdate))//如果未选择，则默认为今天
            pickdate = DateUtil.calendar2yyyyMMDD(Calendar.getInstance());
        if (readDataFromDB(pickdate)) return;
        loadDataFromInternet(pickdate);
    }

    private void loadDataFromInternet(String pickdate) {
        //从互联网中加载数据
        LoaderFactory.getContentLoader().loadContent(Constant.BEFORE_NEWS_HEADER + pickdate,
                new OnContentLoadingFinishedListener() {
                    @Override
                    public void onFinish(String content) {
                        BeforeStoryListBean beforeStory = (BeforeStoryListBean) GsonDecoder.getDecoder().decoding(content, BeforeStoryListBean.class);
                        if (beforeStory != null) {
                            CleanBeforeStoryListBean cleanBeforeStoryListBean = CleanDataHelper.cleanOrignalStory(beforeStory);
                            if (cleanBeforeStoryListBean == null) return;
                            mDatas = cleanBeforeStoryListBean.getSimpleStoryList();
                            writeIntoDB(mDatas);
                            mLvBefore.setAdapter(new UniversalStoryListAdapter(mDatas, mContext));
                        }
                    }
                }
        );
    }

    private boolean readDataFromDB(String pickdate) {
        List<SimpleStory> simpleStoryList = DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).querySimpleStoryByDate(pickdate, dbFlag);
        if (simpleStoryList == null) return false;
        Log.e(LOG_TAG, "simpleStoryList.SIZE:" + simpleStoryList.size());
        if (simpleStoryList.size() >= 0) {
            mLvBefore.setAdapter(new UniversalStoryListAdapter(simpleStoryList, mContext));
            return true;
        }
        return false;
    }

    private int writeIntoDB(List<SimpleStory> simpleStoryList) {
        int resultCode = 1;
        if (simpleStoryList != null && mContext != null) {
            for (int i = 0; i < simpleStoryList.size(); i++) {
                final SimpleStory simpleStory = simpleStoryList.get(i);
                //SimpleStory写入数据库
                int resultCodeFlag = (int) DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).addSimpleStory(simpleStory, dbFlag);
                Log.e(LOG_TAG, "resultCodeFlag:" + resultCodeFlag);
                //评论写入数据库
                /*LoaderFactory.getContentLoaderVolley().loadContent(mContext, Constant.EXTRA_HEAD + simpleStory.getStoryId(), new OnContentLoadingFinishedListener() {
                    @Override
                    public void onFinish(String content) {
                        StoryExtra extra = (StoryExtra) GsonDecoder.getDecoder().decoding(content, StoryExtra.class);
                        ContentValues cv = new ContentValues();
                        cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_POPULARITY, extra.getPopularity());
                        cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_LONG_COMMENTS, extra.getLong_comments());
                        cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_SHORT_COMMENTS, extra.getShort_comments());
                        DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).updateSimpleStory(simpleStory.getStoryId(), cv, dbFlag);
                    }
                });*/
                if (resultCodeFlag < 0)
                    resultCode = resultCodeFlag;
            }
        }
        if (resultCode >= 0)
            Toast.makeText(mContext, "数据成功", Toast.LENGTH_SHORT).show();
        return resultCode;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleStory storiesBean = (SimpleStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, storiesBean.getStoryId());
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        ActionBar ab = activity.getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }

    private boolean shouldShownMCV = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pickDate:
                initDatePicker();
                break;
            case R.id.btn_loadsetting:
                shouldShownMCV = !shouldShownMCV;
                if (shouldShownMCV) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示").setMessage("即将出现新闻日历表，点击日历项，即可缓存当天新闻列表及新闻内容。").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMCV.setVisibility(View.VISIBLE);
                            mBtnLoadSetting.setText("隐藏缓存设置");
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                } else {
                    mMCV.setVisibility(View.GONE);
                    mBtnLoadSetting.setText("缓存设置");
                }
                break;
        }
        updateDateDisplay();
    }

    private void updateDateDisplay() {
        mTvDateDisplay.setText(pickdate);
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                pickdate = year + (monthOfYear + 1 >= 10 ? "" : "0") + monthOfYear + (dayOfMonth >= 10 ? "" : "0") + dayOfMonth;
                Toast.makeText(mContext, pickdate, Toast.LENGTH_SHORT).show();
                loadData();
            }
        }, year, month, day);
        dialog.show();
        DatePicker datePicker = dialog.getDatePicker();
        setDatePickerMaxMinDate(datePicker);
    }

    private void initMaterialCalendarView(MaterialCalendarView mcv) {
        setCalendarViewMaxMinDate(mcv);
        initDecorator(mcv);
        mcv.setVisibility(View.GONE);
        mcv.setOnDateChangedListener(mOnDateSelectedListener);
    }

    private void initDecorator(MaterialCalendarView mcv) {
        downloadedHighLightDecorator = new DownloadedHighLightDecorator();
        mcv.addDecorator(downloadedHighLightDecorator);
        List<String> dateList = DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).queryDate(2);
        for (int i = 0; i < dateList.size(); i++) {
            downloadedHighLightDecorator.addDatesAndUpdate(dateList.get(i));
        }
        mcv.invalidateDecorators();
    }

    private void setDatePickerMaxMinDate(DatePicker datePicker) {
        Calendar today = Calendar.getInstance();
        datePicker.setMaxDate(today.getTimeInMillis());
        today.set(2013, 5 - 1, 20);
        Calendar miniDay = today;
        datePicker.setMinDate(miniDay.getTimeInMillis());
    }

    private void setCalendarViewMaxMinDate(MaterialCalendarView mcv) {
        Calendar today = Calendar.getInstance();
        mcv.setMaximumDate(today);
        today.set(2013, 5 - 1, 20);
        Calendar miniDay = today;
        mcv.setMinimumDate(miniDay);
    }

    private OnDateSelectedListener mOnDateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            if (selected && mContext != null) {
                if (!downloadedHighLightDecorator.contains(new CalendarDay(date.getCalendar()))) {//未下载
                    downloadedHighLightDecorator.addDatesAndUpdate(DateUtil.calendar2yyyyMMDD(date.getCalendar()));
                    StoryDownloader.downloadBefore(date.getCalendar(), mContext, null);
                    widget.invalidateDecorators();
                } else {//已下载
                    pickdate = DateUtil.calendar2yyyyMMDD(date.getCalendar());
                    loadData();
                }
            }
        }
    };
   /* private OnCleanBeforeStoryListBeanListener mOnCleanBeforeStoryListBeanListener = new OnCleanBeforeStoryListBeanListener() {
        @Override
        public void onFinish(CleanBeforeStoryListBean cleanBeforeStoryListBean) {
            if (cleanBeforeStoryListBean == null || cleanBeforeStoryListBean.getSimpleStoryList() == null)
                return;
            mLvBefore.setAdapter(new UniversalStoryListAdapter<>(cleanBeforeStoryListBean.getSimpleStoryList(), mContext));
        }
    };*/
}
