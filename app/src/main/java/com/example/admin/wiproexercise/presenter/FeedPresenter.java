package com.example.admin.wiproexercise.presenter;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.example.admin.wiproexercise.R;
import com.example.admin.wiproexercise.activity.MainActivity;
import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.database.AppDatabase;
import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.model.Row;
import com.example.admin.wiproexercise.retrofit.APIClient;
import com.example.admin.wiproexercise.retrofit.APIInterface;
import com.example.admin.wiproexercise.utils.LocalData;
import com.example.admin.wiproexercise.utils.MyApplication;
import com.example.admin.wiproexercise.utils.Utils;

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
    public void requestDataFromServer() {
        if (view != null) {
            view.showProgress();
        }

        getFeedList();
    }

    public void getFeedList() {

        if (Utils.getDataConnection(MyApplication.getContext())) {

            /** Create handle for the RetrofitInstance interface*/
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            /** Call the method with parameter in the interface to get the feeds data*/
            Call<Feeds> call = apiInterface.getFeeds();

            /**Log the URL called*/
            Log.d(TAG, "URL Called " + call.request().url() + "");

            call.enqueue(new Callback<Feeds>() {
                @Override
                public void onResponse(Call<Feeds> call, retrofit2.Response<Feeds> response) {
                    AppDatabase.getInstance(MyApplication.getContext()).feedsDao().delete();
                    AppDatabase.getInstance(MyApplication.getContext()).feedsDao().insertAll(response.body().getRows());
                    LocalData.getInstance(MyApplication.getContext()).setTitle(response.body().getTitle());

                    if (view != null) {
                        view.setDataToView(response.body().getRows(), response.body().getTitle());
                        view.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<Feeds> call, Throwable t) {
                    if (view != null) {
                        view.onFailure(t.getMessage());
                        view.hideProgress();
                    }
                }
            });

        } else {
            Log.d(TAG, "Offline data");

            List<Row> feeds = AppDatabase.getInstance(MyApplication.getContext()).feedsDao().getAll();
            String title = LocalData.getInstance(MyApplication.getContext()).getTitle();

            if (view != null) {

                if (feeds.size() > 0)
                    view.setDataToView(feeds, title);
                else
                    view.onFailure(MyApplication.getContext().getResources().getString(R.string.no_internet));

                view.hideProgress();
            }
        }
    }
}
