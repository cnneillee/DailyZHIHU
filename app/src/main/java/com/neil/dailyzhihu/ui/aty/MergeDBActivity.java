package com.neil.dailyzhihu.ui.aty;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.db.MergeDB;
import com.neil.dailyzhihu.utils.db.MergeProgressListener;

/**
 * Created by Neil on 2016/5/7.
 */
public class MergeDBActivity extends AppCompatActivity {
    private TextView mTextViewPG;
    private Button btnMerge;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mergedb);
        mTextViewPG = (TextView) findViewById(R.id.tv_merge_pg);
        btnMerge = (Button) findViewById(R.id.btn_merge);
        btnMerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merge();
            }
        });
    }

    public void merge() {
        MergeDB.merge(this, new MergeProgressListener() {
            @Override
            public void onStarted() {
                mProgressDialog = new ProgressDialog(MergeDBActivity.this);
                mProgressDialog.show();
                mProgressDialog.setMessage(updateProgressDisplay("onStarted"));
            }

            @Override
            public void onProgressing(String str) {
                mProgressDialog.setMessage(updateProgressDisplay(str));
            }

            @Override
            public void onCompleted() {
                mProgressDialog.setMessage(updateProgressDisplay("onCompleted"));
                mProgressDialog.dismiss();
            }
        });
    }

    private String updateProgressDisplay(String s) {
        StringBuffer sb = new StringBuffer(mTextViewPG.getText());
        sb.append(s + "\n");
        mTextViewPG.setText(sb.toString());
        return sb.toString();
    }

}
