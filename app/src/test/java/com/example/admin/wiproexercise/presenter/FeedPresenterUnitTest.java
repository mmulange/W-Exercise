package com.example.admin.wiproexercise.presenter;

import android.content.Context;

import com.example.admin.wiproexercise.ConstantsUnitTest;
import com.example.admin.wiproexercise.contractor.MainContract;
import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.model.Row;
import com.example.admin.wiproexercise.presenter.FeedPresenter;
import com.example.admin.wiproexercise.retrofit.APIConstant;
import com.example.admin.wiproexercise.retrofit.APIInterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedPresenterUnitTest {

    @Mock
    MainContract.View feedView;

    MainContract.Presenter presenter;

    private Context context;
    private File httpCacheDirectory;

    @Before
    public void setup() {
        context = mock(Context.class);
        httpCacheDirectory = new File(context.getCacheDir(), APIConstant.HTTP_CACHE);
        presenter = new FeedPresenter(feedView);
    }

    @Test
    public void getFeeds() {
        presenter.requestDataFromServer(httpCacheDirectory);

        List<Row> feeds = new ArrayList<>();
        Row r = new Row();
        r.setTitle(ConstantsUnitTest.FEED_TITLE);
        r.setDescription(ConstantsUnitTest.FEED_DESCRIPTION);
        r.setImageHref(ConstantsUnitTest.FEED_IMAGE_REF);
        feeds.add(r);
        presenter.setFeedList(feeds, ConstantsUnitTest.TITLE);

        verify(feedView).showProgress();
        verify(feedView).setDataToView(feeds, ConstantsUnitTest.TITLE);
        verify(feedView).hideProgress();
    }


    @Test
    public void getFailure() {
        presenter.requestDataFromServer(httpCacheDirectory);
        presenter.setError(ConstantsUnitTest.ERROR);

        verify(feedView).showProgress();
        verify(feedView).onFailure(ConstantsUnitTest.ERROR);
        verify(feedView).hideProgress();
    }
}
