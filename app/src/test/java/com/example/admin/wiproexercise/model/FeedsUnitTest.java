package com.example.admin.wiproexercise.model;

import com.example.admin.wiproexercise.ConstantsUnitTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class FeedsUnitTest {

    @Test
    public void testEmptyList() {
        Feeds feeds = new Feeds();

        List<Row> feedList = feeds.getRows();
        assertEquals("Feed list should be null", feedList, null);

        List<Row> newFeedList = new ArrayList<>();
        feeds.setRows(newFeedList);
        assertThat(feeds.getRows().isEmpty(), is(true));
    }

    @Test
    public void testNonEmptyList() {
        Feeds feeds = new Feeds();

        List<Row> feedList = new ArrayList<>();
        Row row = new Row();
        row.setTitle(ConstantsUnitTest.FEED_TITLE);
        row.setDescription(ConstantsUnitTest.FEED_DESCRIPTION);
        row.setImageHref(ConstantsUnitTest.FEED_IMAGE_REF);
        feedList.add(row);

        feeds.setRows(feedList);

        assertThat(feeds.getRows().isEmpty(), is(false));
    }
}
