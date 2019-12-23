package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDao implements CrdDao<Tweet, Long> {

  private static final String BASE_URL = "https://api.twitter.com/1.1/";
  private static final String POST_URL = BASE_URL + "statuses/update.json";
  private static final String SHOW_URL = BASE_URL + "statuses/show/";
  private static final String DELETE_URL = BASE_URL + "statuses/destroy/";

  private static final int HTTP_OK = 200;

  private TwitterHttpHelper httpHelper;
  private Logger daoLogger = LoggerFactory.getLogger(TwitterDao.class);

  /**
   * Default Constructor, gets the OAuth values for TwitterHttpHelper itself.
   */
  public TwitterDao() {
    httpHelper = new TwitterHttpHelper(System.getenv("CONSUMER_TOKEN"),
        System.getenv("CONSUMER_SECRET"),
        System.getenv("ACCESS_TOKEN"),
        System.getenv("ACCESS_SECRET"));
  }

  /**
   * Alternate constructor if you want to pass in your own TwitterHttpHelper.
   *
   * @param helper a TwitterHttpHelper
   */
  public TwitterDao(TwitterHttpHelper helper) {
    httpHelper = helper;
  }

  /**
   * Create an entity(Tweet) to the underlying storage.
   *
   * @param entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet entity) {
    HttpResponse response;
    try {
      response = httpHelper.httpPost(new URI(POST_URL), entity);
      return JsonUtil.toObject(EntityUtils.toString(response.getEntity()), Tweet.class);
    } catch (URISyntaxException uriex) {
      daoLogger.error("Malformed URI " + POST_URL);
      throw new IllegalArgumentException(uriex.getMessage());
    } catch (IOException ex) {
      daoLogger.error("Failed to parse response\n" + ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Find an entity(Tweet) by its id.
   *
   * @param tweetId entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(Long tweetId) {
    URI find;
    try {
      find = new URI(SHOW_URL + tweetId + ".json");
    } catch (URISyntaxException uriex) {
      throw new IllegalArgumentException(uriex.getMessage());
    }
    try {
      return JsonUtil.toObject(EntityUtils.toString(httpHelper.httpGet(find).getEntity()),
          Tweet.class);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Tweet does not exist, or you may not have permission to "
          + "view it\nID: " + tweetId);
    }
  }

  /**
   * Delete an entity(Tweet) by its ID.
   *
   * @param tweetId of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(Long tweetId) {
    URI delete;
    try {
      delete = new URI(DELETE_URL + tweetId + ".json");
    } catch (URISyntaxException uriex) {
      throw new IllegalArgumentException(uriex.getMessage());
    }
    try {
      return JsonUtil
          .toObject(EntityUtils.toString(httpHelper.httpPost(delete).getEntity()), Tweet.class);
    } catch (IOException ex) {
      throw new IllegalArgumentException("You are not allowed to delete this tweet, or it doesn't "
          + "exist.\nID: " + tweetId);
    }
  }
}
