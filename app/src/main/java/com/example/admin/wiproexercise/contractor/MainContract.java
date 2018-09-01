package com.example.admin.wiproexercise.contractor;


import com.example.admin.wiproexercise.model.Row;

import java.io.File;
import java.util.List;

public class MainContract {

    /**
     * Represents the View.
     * <p>
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToView and onFailure displaying data to view.
     */
    public interface View {
        void showProgress();

        void hideProgress();

        void setDataToView(List<Row> feedList, String title);

        void onFailure(String message);
    }

    /**
     * Represents the Presenter.
     * Call when user interact with the view.
     */
    public interface Presenter {

        void onDestroy();

        void requestDataFromServer(File httpCacheDirectory);

        void setFeedList(List<Row> feeds, String title);

        void setError(String message);
    }
}
