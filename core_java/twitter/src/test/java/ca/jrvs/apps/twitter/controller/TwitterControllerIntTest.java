package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import ca.jrvs.apps.twitter.model.Tweet;
import java.time.Instant;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwitterControllerIntTest {

  static TwitterController controller;
  long tweetId;
  String tweetIdStr;

  @BeforeClass
  public static void setUp() {
    controller = new TwitterController();
  }

  @Test
  public void postTweet() {
    String[] testText = {"Test tweet with no Geo" + Instant.now()};
    String[] testTextWithGeo = {"Test tweet with Geo" + Instant.now(), "123.115", "-54"};
    Tweet tweetNoGeo = controller.postTweet(testText);
    Tweet tweetWithGeo = controller.postTweet(testTextWithGeo);
    assertNotNull(tweetNoGeo);
    assertNotNull(tweetWithGeo);

    controller.deleteTweet(new String[]{tweetNoGeo.getIdStr(), tweetWithGeo.getIdStr()});
  }

  public void postTweetForTests() {
    Tweet testTweet = controller.postTweet(new String[]{"Test tweet at " + Instant.now()});
    tweetId = testTweet.getId();
    tweetIdStr = testTweet.getIdStr();
  }

  @Test
  public void showTweet() {
    String[] filterArgs;
    Tweet testTweet;
    postTweetForTests();
    filterArgs = new String[]{tweetIdStr, "id", "idStr"};

    testTweet = controller.showTweet(new String[]{tweetIdStr});
    assertNotNull(testTweet);
    testTweet = controller.showTweet(filterArgs);
    assertNotNull(testTweet);
    assertNull(testTweet.getText());

    controller.deleteTweet(new String[]{tweetIdStr});
  }

  @Test
  public void deleteTweet() {
    postTweetForTests();
    assertNotNull(controller.deleteTweet(new String[]{tweetIdStr}));
  }
}