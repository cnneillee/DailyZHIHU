package com.neil.dailyzhihu.ui.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseFragment;
import com.neil.dailyzhihu.model.bean.orignal.OriginalStory;
import com.neil.dailyzhihu.model.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.MainFragmentPresenter;
import com.neil.dailyzhihu.presenter.constract.MainFragmentContract;
import com.neil.dailyzhihu.ui.adapter.PastStoryListBaseAdapter;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;
import com.neil.dailyzhihu.ui.widget.DownloadedHighLightDecorator;
import com.neil.dailyzhihu.utils.date.DateInNumbers;
import com.neil.dailyzhihu.utils.date.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class PastFragment extends BaseFragment<MainFragmentPresenter> implements AdapterView.OnItemClickListener,
        ObservableScrollViewCallbacks, View.OnClickListener, MainFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = PastFragment.class.getSimpleName();

    @BindView(R.id.lv_before)
    ObservableListView mLvBefore;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private PastStoryListBaseAdapter mBeforeAdapter;
    List<PastStoryListBean.PastStory> mBeforeStoryList;

    /*选中的日期，格式为：yyyyMMDD，如20161002*/
    private String pickedDate;
    private DownloadedHighLightDecorator downloadedHighLightDecorator;

    private Button mBtnLoadSetting;
    private MaterialCalendarView mMCV;
    private TextView mTvDateDisplay;

    private boolean shouldShownMCV = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_past;
    }

    @Override
    protected void initEventAndData() {
        View header = LayoutInflater.from(mContext).inflate(R.layout.fragment_past_header, null);
        Button btnPickDate = (Button) header.findViewById(R.id.btn_pickDate);
        mBtnLoadSetting = (Button) header.findViewById(R.id.btn_loadsetting);
        mMCV = (MaterialCalendarView) header.findViewById(R.id.material_calendarview);
        mTvDateDisplay = (TextView) header.findViewById(R.id.tv_dateDisplay);
        mLvBefore.addHeaderView(header);

        mLvBefore.setOnItemClickListener(this);
        mLvBefore.setScrollViewCallbacks(this);
        btnPickDate.setOnClickListener(this);
        mTvDateDisplay.setOnClickListener(this);
        mBtnLoadSetting.setOnClickListener(this);
        mSrlRefresh.setOnRefreshListener(this);

        // 初始化MaterialCalendarView
        // init Decorator
        downloadedHighLightDecorator = new DownloadedHighLightDecorator();
        mMCV.addDecorator(downloadedHighLightDecorator);
        mMCV.invalidateDecorators();

        mMCV.setVisibility(View.GONE);
        mMCV.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (!selected || mContext == null) return;
                if (!downloadedHighLightDecorator.contains(new CalendarDay(date.getCalendar()))) {//未下载
                    downloadedHighLightDecorator.addDatesAndUpdate(DateUtil.calendar2yyyyMMDD(date.getCalendar()));
                    widget.invalidateDecorators();
                } else {//已下载
                    pickedDate = DateUtil.calendar2yyyyMMDD(date.getCalendar());
                    mPresenter.getNewsListData(MainFragmentContract.PAST, pickedDate);
                }
            }
        });

        mBeforeStoryList = new ArrayList<>();
        mBeforeAdapter = new PastStoryListBaseAdapter(mContext, mBeforeStoryList);
        mLvBefore.setAdapter(mBeforeAdapter);

        // 初始化今天
        pickedDate = DateUtil.calendar2yyyyMMDD(Calendar.getInstance());
        mPresenter.getNewsListData(MainFragmentContract.PAST, pickedDate);
        mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void showContent(OriginalStory bean) {
        mSrlRefresh.setRefreshing(false);
        List<PastStoryListBean.PastStory> beforeStoryList = ((PastStoryListBean) bean).getStories();

        mBeforeStoryList.clear();
        for (int i = 0; i < beforeStoryList.size(); i++) {
            mBeforeStoryList.add(beforeStoryList.get(i));
        }
        mBeforeAdapter.notifyDataSetChanged();

        updateDateDisplay();
    }

    @Override
    public void showError(String errorMsg) {
        if (mSrlRefresh != null) mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(OriginalStory content) {
        showContent(content);
    }

    @Override
    public void onRefresh() {
        mPresenter.getNewsListData(MainFragmentContract.PAST, pickedDate);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PastStoryListBean.PastStory pastStory = (PastStoryListBean.PastStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, pastStory.getStoryId());
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, pastStory.getImage());
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pickDate:
                pickDate();
                break;
            case R.id.tv_dateDisplay:
                pickDate();
                break;
            case R.id.btn_loadsetting:
                shouldShownMCV = !shouldShownMCV;
                if (shouldShownMCV) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(getResources().getString(R.string.prompt))
                            .setMessage(getResources().getString(R.string.cache_past_news_msg))
                            .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mMCV.setVisibility(View.VISIBLE);
                                    mBtnLoadSetting.setText(getResources().getString(R.string.hide_cache_setting));
                                }
                            }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                } else {
                    mMCV.setVisibility(View.GONE);
                    mBtnLoadSetting.setText(getResources().getString(R.string.cache_setting));
                }
                break;
        }
    }

    private void updateDateDisplay() {
        mTvDateDisplay.setText(pickedDate);
    }

    private void pickDate() {
        DateInNumbers dateInNumbers = DateUtil.yyyyMMDD2DateInNumbers(pickedDate);
        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                pickedDate = DateUtil.dateInNumbers2yyyyMMDD(year, monthOfYear + 1, dayOfMonth);
                Toast.makeText(mContext, pickedDate, Toast.LENGTH_SHORT).show();
                mPresenter.getNewsListData(MainFragmentContract.PAST, pickedDate);
            }
        }, dateInNumbers.getYear(), dateInNumbers.getMonthOfYear() - 1, dateInNumbers.getDayOfMonth());
        dialog.show();

        // 设置日期选择器的日期选择区间（最大为今日，最小为2013年5月20日）
        DatePicker datePicker = dialog.getDatePicker();
        Calendar today = Calendar.getInstance();
        datePicker.setMaxDate(today.getTimeInMillis());
        today.set(Constant.MIN_YEAR_OF_PAST_STORY, Constant.MIN_MONTH_OF_YEAR_OF_PAST_STORY - 1, Constant.MIN_DAY_OF_MONTH_OF_PAST_STORY);
        datePicker.setMinDate(today.getTimeInMillis());
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

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    /* 懒加载相关 */

    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
    // 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;

    //标志当前页面是否可见
    private boolean isVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //getData();//数据请求
        mPresenter.getNewsListData(MainFragmentContract.PAST, pickedDate);
    }

    protected void onInvisible() {
    }
}
