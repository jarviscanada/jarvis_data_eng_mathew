package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  TwitterHttpHelper mockHttpHelper;
  @InjectMocks
  TwitterDao twitterDao = new TwitterDao();
  @Mock
  Tweet mockTweet;
  @Mock
  HttpResponse mockHttpResponse;
  long mockTweetId = 123456789101112L;
  String jsonString = "{\"created_at\":\"Mon Feb 13 12:22:45 +0000 2019\",\"id\":123456789101112,"
      + "\"id_str\":\"123456789101112\",\"text\":\"ThisIsSomeText\",\"entities\":{\"hashtags\":[],"
      + "\"user_mentions\":[]},\"coordinates\":null,\"retweet_count\":0,\"favorite_count\":0,"
      + "\"favorited\":false,\"retweeted\":false}";

  @Before
  public void prepMocks() {
    try {
      when(mockHttpResponse.getEntity()).thenReturn(new StringEntity(jsonString));
      when(mockHttpHelper.httpPost(any(URI.class))).thenReturn(mockHttpResponse);
      when(mockHttpHelper.httpGet(any(URI.class))).thenReturn(mockHttpResponse);
      when(mockHttpHelper.httpPost(any(URI.class))).thenReturn(mockHttpResponse);
    } catch (UnsupportedEncodingException ueex) {
      throw new RuntimeException(ueex.getMessage());
    }
  }

  @Test
  public void create() {
    Tweet returnTweet;
    returnTweet = twitterDao.create(mockTweet);
    LoggerFactory.getLogger("").info(returnTweet.toString());
    assertEquals(123456789101112L, returnTweet.getId());
  }

  @Test
  public void findById() {
    Tweet response;
    response = twitterDao.findById(mockTweetId);
    LoggerFactory.getLogger("").info(response.toString());
    assertEquals(123456789101112L, response.getId() );
  }

  @Test
  public void deleteById() {
    Tweet response = twitterDao.deleteById(mockTweetId);
    LoggerFactory.getLogger("").info(response.toString());
    assertEquals(123456789101112L, response.getId());
  }

}
