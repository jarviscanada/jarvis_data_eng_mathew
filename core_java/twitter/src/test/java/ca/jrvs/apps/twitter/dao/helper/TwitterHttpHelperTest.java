package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class TwitterHttpHelperTest {

  @Test
  public void httpPost() throws Exception {
    HttpHelper helper = new TwitterHttpHelper(System.getenv("CONSUMER_TOKEN"),
        System.getenv("CONSUMER_SECRET"),
        System.getenv("ACCESS_TOKEN"),
        System.getenv("ACCESS_SECRET"));

    HttpResponse response = helper.httpPost(new URI(
        "https://api.twitter.com/1.1/statuses/update.json?status=I%20did%20this%20with%20JUnit4"));
    LoggerFactory.getLogger("").info(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void httpGet() throws Exception {
    HttpHelper helper = new TwitterHttpHelper(System.getenv("CONSUMER_TOKEN"),
        System.getenv("CONSUMER_SECRET"),
        System.getenv("ACCESS_TOKEN"),
        System.getenv("ACCESS_SECRET"));

    HttpResponse response = helper.httpGet(new URI(
        "https://api.twitter.com/1.1/statuses/show.json?id=1206640438289092609"));
    LoggerFactory.getLogger("").info(EntityUtils.toString(response.getEntity()));
  }
}