package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;


public class TwitterDaoTest {

  TwitterDao twitterDao;
  Tweet someTweetToPost;
  long someTweetToFind;

  @Before
  public void makeTweet() {
    someTweetToPost = new Tweet();
    someTweetToPost.setText("This is a JUnit test " + Instant.now().toString());
    someTweetToFind = new TwitterDao().create(someTweetToPost).getId();
    LoggerFactory.getLogger("Tweet Response").info("Tweet response gives ID " + someTweetToFind);
    someTweetToPost.setText("This is another JUnit test " + Instant.now().toString());
  }

  @Test
  public void create() {
    twitterDao = new TwitterDao();
    Tweet response = twitterDao.create(someTweetToPost);
    LoggerFactory.getLogger("").info(response.toString());
  }

  @Test
  public void findById() {
    twitterDao = new TwitterDao();
    Tweet response = twitterDao.findById(someTweetToFind);
    LoggerFactory.getLogger("").info(response.toString());
  }

  @Test
  public void deleteById() {
    twitterDao = new TwitterDao();
    Tweet response = twitterDao.deleteById(someTweetToFind);
    LoggerFactory.getLogger("").info(response.toString());
  }
}