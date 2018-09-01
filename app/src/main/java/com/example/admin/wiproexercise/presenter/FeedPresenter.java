package com.example.admin.wiproexercise.presenter;


import android.util.Log;

import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.model.Row;
import com.example.admin.wiproexercise.retrofit.APIClient;
import com.example.admin.wiproexercise.retrofit.APIInterface;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by  Maroti Mulange on 22-08-2018.
 * <p>
 * An implementation of the feed presenter.
 */

public class FeedPresenter implements MainContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private MainContract.View view;

    public FeedPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestDataFromServer(File httpCacheDirectory) {
        if (view != null) {
            view.showProgress();
        }

        getFeedList(httpCacheDirectory);
    }

    @Override
    public void setFeedList(List<Row> feeds, String title) {
        if (view != null) {
            view.setDataToView(feeds, title);
            view.hideProgress();
        }
    }

    @Override
    public void setError(String message) {
        if (view != null) {
            view.onFailure(message);
            view.hideProgress();
        }
    }

    public void getFeedList(File httpCacheDirectory) {

        /** Create handle for the RetrofitInstance interface*/
        APIInterface apiInterface = APIClient.getClient(httpCacheDirectory).create(APIInterface.class);

        /** Call the method with parameter in the interface to get the feeds data*/
        Call<Feeds> call = apiInterface.getFeeds();

        /**Log the URL called*/
        //Log.d(TAG, "URL Called " + call.request().url() + "");

        call.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, retrofit2.Response<Feeds> response) {
                if (response.isSuccessful())
                    setFeedList(response.body().getRows(), response.body().getTitle());
                else
                    setError(response.message());
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }
}
