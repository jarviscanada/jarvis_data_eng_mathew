package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.model.GeoLoc;
import ca.jrvs.apps.twitter.model.Tweet;
import java.time.Instant;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  TwitterService testService = new TwitterService();
  Tweet goodTweet;
  String tweetId;

  @Before
  public void setUp() {
    goodTweet = new Tweet();
    GeoLoc loc = new GeoLoc();
    loc.setCoordinates(new float[]{25.0F, 32.15F});
    goodTweet.setLocation(loc);
    goodTweet.setText("TwitterService test at " + Instant.now());
  }

  @Test
  public void createTweet() {
    Tweet returnedTweet;
    returnedTweet = testService.postTweet(goodTweet);
    tweetId = returnedTweet.getIdStr();
    Assert.assertNotNull(returnedTweet);
  }

  @Test
  public void showTweet() {
    Tweet returnedTweet;
    createTweet();
    returnedTweet = testService.showTweet(tweetId, new String[]{});
    Assert.assertNotNull(returnedTweet);
    testService.deleteTweets(new String[]{tweetId});
  }

  @Test
  public void deleteTweet_GoodBad() {
    List<Tweet> deletedTweets;
    createTweet();
    deletedTweets = testService.deleteTweets(new String[]{"9999999999", tweetId});
    Assert.assertNotNull(deletedTweets.get(0));
  }

}
