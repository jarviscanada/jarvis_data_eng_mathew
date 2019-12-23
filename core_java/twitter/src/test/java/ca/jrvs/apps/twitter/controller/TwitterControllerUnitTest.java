package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.GeoLoc;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  TwitterService mockService;
  @InjectMocks
  TwitterController testController;
  Tweet goodTweet;

  @Before
  public void setUp() {
    GeoLoc location = new GeoLoc();
    location.setCoordinates(new float[]{123.05F, -52.03F});
    goodTweet = new Tweet();
    goodTweet.setText("This is mock Tweet Text");
    goodTweet.setId(123456123456L);
    goodTweet.setIdStr("123456123456");
    goodTweet.setLocation(location);
  }

  @Test
  public void postTweet() {
    when(mockService.postTweet(any())).thenReturn(goodTweet);
    String[] textOnly = {"This is some tweet text"};
    String[] textWithGeo = {"This tweet has geo", "123", "-32.58"};

    assertNotNull(testController.postTweet(textOnly));
    assertNotNull(testController.postTweet(textWithGeo));
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweet_NoArgs() {
    testController.postTweet(new String[]{});
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweet_TwoArgs() {
    testController.postTweet(new String[]{"some text", "15"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweet_TooManyArgs() {
    testController.postTweet(new String[]{"this is some text", "123.123", "10", "-19875"});
  }

  @Test
  public void showTweet() {
    when(mockService.showTweet(any(), any())).thenReturn(goodTweet);
    assertNotNull(testController.showTweet(new String[]{"123456789"}));
  }

  @Test
  public void showTweet_WithFilters() {
    when(mockService.showTweet(any(), any())).thenReturn(goodTweet);
    assertNotNull(testController.showTweet(new String[]{"123456789", "id", "idStr", "text"}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void showTweet_NoArgs() {
    testController.showTweet(new String[]{});
  }

  @Test
  public void deleteTweet() {
    List<Tweet> tweetList = new ArrayList<>();
    tweetList.add(goodTweet);
    when(mockService.deleteTweets(any())).thenReturn(tweetList);
    tweetList = testController.deleteTweet(new String[]{"1234567891"});
    assertFalse(tweetList.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteTweet_NoArgs() {
    testController.deleteTweet(new String[]{});
  }
}