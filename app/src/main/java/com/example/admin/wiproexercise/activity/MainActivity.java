package com.example.admin.wiproexercise.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wiproexercise.R;
import com.example.admin.wiproexercise.adapter.FeedsAdapter;
import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.model.Row;
import com.example.admin.wiproexercise.presenter.FeedPresenter;
import com.example.admin.wiproexercise.retrofit.APIConstant;
import com.example.admin.wiproexercise.utils.UtilDialog;

import java.io.File;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by  Maroti Mulange on 21-08-2018.
 * <p>
 * Displays photos with heading and descriptions.
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.activity_main_rv)
    protected RecyclerView rvFeeds;

    @BindView(R.id.activity_main_swiperefresh)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.activity_main_tv_title)
    protected TextView tvTitle;
    @BindView(R.id.activity_main_tv_error)
    protected TextView tvError;

    @BindString(R.string.app_name)
    protected String appName;
    @BindString(R.string.loading)
    protected String loading;
    @BindString(R.string.no_internet)
    protected String noInternet;
    @BindString(R.string.no_feeds_available)
    protected String noFeedsAvailable;

    private UtilDialog utilDialog;
    private FeedsAdapter feedsAdapter;
    private MainContract.Presenter presenter;
    private File httpCacheDirectory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initParameters();
        initViews();

        presenter = new FeedPresenter(this);
        presenter.requestDataFromServer(httpCacheDirectory);
    }

    public void initParameters() {
        httpCacheDirectory = new File(this.getCacheDir(), APIConstant.HTTP_CACHE);
        utilDialog = UtilDialog.getInstance(this);
    }

    private void initViews() {

        tvError.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFeeds.setLayoutManager(mLayoutManager);
        rvFeeds.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestDataFromServer(httpCacheDirectory);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @OnClick(R.id.activity_main_iv_refresh)
    public void refreshData() {
        presenter.requestDataFromServer(httpCacheDirectory);
    }

    @Override
    public void showProgress() {
        utilDialog.showDialog(appName, loading, UtilDialog.PROGRESS_DIALOG);
    }

    @Override
    public void hideProgress() {
        utilDialog.dismissDialog(UtilDialog.PROGRESS_DIALOG);
    }

    @Override
    public void setDataToView(List<Row> feedList, String title) {
        Log.d(TAG + ".onSuccess()", "feedList :" + feedList.size());

        tvTitle.setText(title);

        if (feedList.size() > 0) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);

            feedsAdapter = new FeedsAdapter(feedList);
            rvFeeds.setAdapter(feedsAdapter);
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(noFeedsAvailable);
        }
    }

    @Override
    public void onFailure(String message) {
        swipeRefreshLayout.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
