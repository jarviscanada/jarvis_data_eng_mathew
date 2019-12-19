package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.GeoLoc;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
  String[] testFields = {"idStr","text","location"};


  @Before
  public void setUp() throws Exception {
    GeoLoc goodLoc = new GeoLoc();
    GeoLoc badLoc = new GeoLoc();
    goodLoc.setCoordinates(new float[]{-42.1235F, 111.111F});
    badLoc.setCoordinates(new float[]{0.000F, 356.25471F});
    Mockito.when(goodTweet.getText()).thenReturn("This is a good tweet");
    Mockito.when(goodTweet.getLocation()).thenReturn(goodLoc);
    Mockito.when(goodTweet.getIdStr()).thenReturn("123456789123");
    Mockito.when(goodTweet.getId()).thenReturn(123456789123L);
    Mockito.when(badTextTweet.getText()).thenReturn("This tweet is way too long.@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    Mockito.when(badTextTweet.getLocation()).thenReturn(goodLoc);
    Mockito.when(badGeoTweet.getText()).thenReturn("This tweet has a bad geo-location");
    Mockito.when(badGeoTweet.getLocation()).thenReturn(badLoc);
  }

  @Test
  public void postTweet() {
    Tweet resultTweet;
    Mockito.when(mockDao.create(goodTweet)).thenReturn(goodTweet);
    Mockito.when(mockDao.create(badGeoTweet)).thenReturn(badGeoTweet);
    Mockito.when(mockDao.create(badTextTweet)).thenReturn(badTextTweet);

    // Good test, Return should be not null
    resultTweet = testService.postTweet(goodTweet);
    Assert.assertNotNull(resultTweet);

    // Bad Geo test, Return should be null
    resultTweet = testService.postTweet(badGeoTweet);
    Assert.assertNull(resultTweet);

    // Bad text test, Return should be null
    resultTweet = testService.postTweet(badTextTweet);
    Assert.assertNull(resultTweet);
  }

  @Test
  public void showTweet() {
    Mockito.when(mockDao.findById(testId)).thenReturn(goodTweet);
    // Good test, no fields to filter out
    Tweet returnedTweet = testService.showTweet(testStrId, new String[0]);
    System.out.println(returnedTweet.toString());
    Assert.assertEquals(testStrId, returnedTweet.getIdStr());

    // Good test, filter fields
    returnedTweet = testService.showTweet(testStrId, testFields);
    System.out.println(returnedTweet.toString());
    Assert.assertNull(returnedTweet.getEntities());
  }

  // Sad tests, illegal argument testing
  @Test(expected = IllegalArgumentException.class)
  public void showTweet_IllegalId(){
    Mockito.when(mockDao.findById(testId)).thenReturn(goodTweet);
    Mockito.when(mockDao.findById(AdditionalMatchers.not(Mockito.eq(testId))))
        .thenThrow(IllegalArgumentException.class);
    // Tweet doesn't exist test, no fields to test
    Tweet returnedTweet = testService.showTweet("1111111111", new String[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void showTweet_IllegalField(){
    Mockito.when(mockDao.findById(testId)).thenReturn(goodTweet);
    // Field to filter for doesn't exist in the Tweet object.
    Tweet returnedTweet = testService
        .showTweet(testStrId, new String[]{"This field doesn't exist"});
  }

  @Test
  public void deleteTweets() {
    List<Tweet> deletedTweets;
    Mockito.when(mockDao.deleteById(testId)).thenReturn(goodTweet);
    deletedTweets = testService.deleteTweets(new String[]{testStrId});
    Assert.assertEquals(deletedTweets.get(0).getIdStr(), testStrId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteTweets_MalformedId(){
    testService.deleteTweets(new String[]{"This will throw IllegalArgument"});
  }

  @Test
  public void deleteTweets_GoodAndBad(){
    List<Tweet> deletedTweets;
    Mockito.when(mockDao.deleteById(testId)).thenReturn(goodTweet);
    Mockito.when(mockDao.deleteById(AdditionalMatchers.not(Mockito.eq(testId))))
        .thenThrow(IllegalArgumentException.class);
    deletedTweets = testService.deleteTweets(new String[]{"111222", testStrId});
    Assert.assertNotNull(deletedTweets.get(0));
  }
}