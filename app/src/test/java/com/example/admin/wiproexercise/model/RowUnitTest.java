package com.example.admin.wiproexercise.model;

import android.app.Person;

import com.example.admin.wiproexercise.ConstantsUnitTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RowUnitTest {

    @Test
    public void testCreation() {
        Row firstRow = new Row();
        assertEquals("Title should be null", firstRow.getTitle(), null);
        assertEquals("Description should be null", firstRow.getDescription(), null);
        assertEquals("ImageHref should be null", firstRow.getImageHref(), null);

        Row secondRow = new Row();
        secondRow.setTitle(ConstantsUnitTest.FEED_TITLE);
        secondRow.setDescription(ConstantsUnitTest.FEED_DESCRIPTION);
        secondRow.setImageHref(ConstantsUnitTest.FEED_IMAGE_REF);

        assertEquals("Title name should be " + ConstantsUnitTest.FEED_TITLE, secondRow.getTitle(), ConstantsUnitTest.FEED_TITLE);
        assertEquals("Description name should be " + ConstantsUnitTest.FEED_DESCRIPTION, secondRow.getDescription(), ConstantsUnitTest.FEED_DESCRIPTION);
        assertEquals("ImageHref name should be " + ConstantsUnitTest.FEED_IMAGE_REF, secondRow.getImageHref(), ConstantsUnitTest.FEED_IMAGE_REF);
    }

    @Test
    public void testUpdateFeed() {
        Row secondRow = new Row();
        secondRow.setTitle(ConstantsUnitTest.FEED_TITLE);
        secondRow.setDescription(ConstantsUnitTest.FEED_DESCRIPTION);
        secondRow.setImageHref(ConstantsUnitTest.FEED_IMAGE_REF);
    }

}
