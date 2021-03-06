package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.GeoLoc;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  TwitterDao mockDao;
  @InjectMocks
  TwitterService testService = new TwitterService(mockDao);
  @Mock
  Tweet goodTweet;
  @Mock
  Tweet badTextTweet;
  @Mock
  Tweet badGeoTweet;
  long testId = 123456789123L;
  String testStrId = "123456789123";
  String[] testFields = {"idStr", "text", "location"};


  @Before
  public void setUp() {
    GeoLoc goodLoc = new GeoLoc();
    goodLoc.setCoordinates(new float[]{-111.111F, 42.1235F});
    Mockito.when(goodTweet.getText()).thenReturn("This is a good tweet");
    Mockito.when(goodTweet.getLocation()).thenReturn(goodLoc);
    Mockito.when(goodTweet.getIdStr()).thenReturn("123456789123");
  }

  @Test
  public void postTweet() {
    Tweet resultTweet;
    Mockito.when(mockDao.create(goodTweet)).thenReturn(goodTweet);
    // Good test, Return should be not null
    resultTweet = testService.postTweet(goodTweet);
    Assert.assertNotNull(resultTweet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweet_BadGeo(){
    Tweet resultTweet;
    GeoLoc badLoc = new GeoLoc();
    badLoc.setCoordinates(new float[]{0.000F, 356.25471F});
    Mockito.when(badGeoTweet.getLocation()).thenReturn(badLoc);
    Mockito.when(badGeoTweet.getText()).thenReturn("This tweet has a bad geo-location");

    // Bad Geo test, Should throw IllegalArgumentException
    resultTweet = testService.postTweet(badGeoTweet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweet_BadText(){
    Tweet resultTweet;
    GeoLoc goodLoc = new GeoLoc();
    goodLoc.setCoordinates(new float[]{-111.111F, 42.1235F});
    Mockito.when(badTextTweet.getText()).thenReturn("This tweet is way too long.@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    Mockito.when(badTextTweet.getLocation()).thenReturn(goodLoc);
    // Bad text test, Should throw IllegalArgumentException
    resultTweet = testService.postTweet(badTextTweet);
  }

  @Test
  public void showTweet() {
    Mockito.when(mockDao.findById(testId)).thenReturn(goodTweet);
    // Good test, no fields to filter out
    Tweet returnedTweet = testService.showTweet(testStrId, new String[0]);
    Assert.assertEquals(testStrId, returnedTweet.getIdStr());

    // Good test, filter fields
    returnedTweet = testService.showTweet(testStrId, testFields);
    Assert.assertNull(returnedTweet.getEntities());
  }

  // Sad tests, illegal argument testing
  @Test(expected = IllegalArgumentException.class)
  public void showTweet_IllegalId() {
    Mockito.when(mockDao.findById(AdditionalMatchers.not(Mockito.eq(testId))))
        .thenThrow(IllegalArgumentException.class);
    // Tweet doesn't exist test, no fields to test
    testService.showTweet("1111111111", new String[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void showTweet_IllegalField() {
    Mockito.when(mockDao.findById(testId)).thenReturn(goodTweet);
    // Field to filter for doesn't exist in the Tweet object.
    testService.showTweet(testStrId, new String[]{"This field doesn't exist"});
  }

  @Test
  public void deleteTweets() {
    List<Tweet> deletedTweets;
    Mockito.when(mockDao.deleteById(testId)).thenReturn(goodTweet);
    deletedTweets = testService.deleteTweets(new String[]{testStrId});
    Assert.assertEquals(deletedTweets.get(0).getIdStr(), testStrId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteTweets_MalformedId() {
    testService.deleteTweets(new String[]{"This will throw IllegalArgument"});
  }

  @Test
  public void deleteTweets_GoodAndBad() {
    List<Tweet> deletedTweets;
    Mockito.when(mockDao.deleteById(testId)).thenReturn(goodTweet);
    Mockito.when(mockDao.deleteById(AdditionalMatchers.not(Mockito.eq(testId))))
        .thenThrow(IllegalArgumentException.class);
    deletedTweets = testService.deleteTweets(new String[]{"111222", testStrId});
    Assert.assertNotNull(deletedTweets.get(0));
  }
}