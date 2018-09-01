package com.example.admin.wiproexercise;

import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.retrofit.APIInterface;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;

public class ApiUnitTest {

    @Test
    public void getFailure() {
        APIInterface mockedApiInterface = Mockito.mock(APIInterface.class);
        Call<Feeds> mockedCall = Mockito.mock(Call.class);

        Mockito.when(mockedApiInterface.getFeeds()).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback<Feeds> callback = (Callback) invocation.getArguments()[0];

                callback.onFailure(mockedCall, new IOException());

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));
    }

    @Test
    public void getFeeds() {

        APIInterface mockedApiInterface = Mockito.mock(APIInterface.class);
        Call<Feeds> mockedCall = Mockito.mock(Call.class);

        Mockito.when(mockedApiInterface.getFeeds()).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback<Feeds> callback = (Callback) invocation.getArguments()[0];

                callback.onResponse(mockedCall, Response.success(new Feeds()));

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));
    }
}
