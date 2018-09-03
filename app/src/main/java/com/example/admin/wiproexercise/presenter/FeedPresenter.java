package com.example.admin.wiproexercise.presenter;


import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.model.Row;
import com.example.admin.wiproexercise.retrofit.APIClient;
import com.example.admin.wiproexercise.retrofit.APIInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
                if (response.isSuccessful()) {

                    List<Row> feeds = response.body().getRows();

                    if (Build.VERSION.SDK_INT >= 24)
                        feeds.removeIf((Row row) -> TextUtils.isEmpty(row.getTitle()) && TextUtils.isEmpty(row.getDescription()) && TextUtils.isEmpty(row.getImageHref()));
                    else
                        feeds = removeEmptyFeeds(feeds);

                    setFeedList(feeds, response.body().getTitle());
                } else
                    setError(response.message());
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    /**
     * Remove empty feeds that do not have any content and image.
     * below api 24
     *
     * @param feeds
     * @return
     */

    public List<Row> removeEmptyFeeds(List<Row> feeds) {
        List<Row> newFeedList = new ArrayList<>();
        for (Row row : feeds) {
            if (!TextUtils.isEmpty(row.getTitle()) || !TextUtils.isEmpty(row.getDescription()) || !TextUtils.isEmpty(row.getImageHref()))
                newFeedList.add(row);
        }
        return newFeedList;
    }
}
