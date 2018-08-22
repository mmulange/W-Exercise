package com.example.admin.wiproexercise.presenter;


import android.content.Context;

import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.model.Row;

import java.util.List;

/**
 * Created by  Maroti Mulange on 22-08-2018.
 * <p>
 * An implementation of the feed presenter.
 */

public class FeedPresenter implements MainContract.Presenter, MainContract.GetFeedDataIntractor.OnFinishedListener {

    private Context context;
    private MainContract.View view;
    private MainContract.GetFeedDataIntractor getFeedDataIntractor;

    public FeedPresenter(Context context, MainContract.View view, MainContract.GetFeedDataIntractor getFeedDataIntractor) {
        this.context = context;
        this.view = view;
        this.getFeedDataIntractor = getFeedDataIntractor;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestDataFromServer(String url) {
        if (view != null) {
            view.showProgress();
        }
        getFeedDataIntractor.getFeedList(context, url, this);
    }

    @Override
    public void onSuccess(List<Row> feedList, String title) {
        if (view != null) {
            view.setDataToView(feedList, title);
            view.hideProgress();
        }
    }

    @Override
    public void onFailure(String message, String url) {
        if (view != null) {
            view.onFailure(message, url);
            view.hideProgress();
        }
    }
}
