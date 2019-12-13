package ca.jrvs.apps.twitter.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TwitterApiTest{

  private static final String OAUTH_CONSUMER_KEY = System.getenv("CONSUMER_TOKEN");
  private static final String OAUTH_CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
  private static final String OAUTH_APPLICATION_KEY = System.getenv("ACCESS_TOKEN");
  private static final String OAUTH_APPLICATION_SECRET = System.getenv("ACCESS_SECRET");

  public TwitterApiTest(){

  }

  public static void main(String[] args) {
    OAuthConsumer oac = new CommonsHttpOAuthConsumer(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET);
    oac.setTokenWithSecret(OAUTH_APPLICATION_KEY, OAUTH_APPLICATION_SECRET);

    String status = "This tweet is another API test";
    HttpPost url = new HttpPost("https://api.twitter.com/1.1/statuses/update.json");
    List<NameValuePair> params = new ArrayList<>();
    params.add(new BasicNameValuePair("status", status));
    try {
      url.setEntity(new UrlEncodedFormEntity(params));
      oac.sign(url);

    } catch (UnsupportedEncodingException | OAuthException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println("HTTP Headers");
    for (Header h : url.getAllHeaders()) {
      System.out.println(h.toString());
    }
    HttpClient client = HttpClientBuilder.create().build();

    try {
      HttpResponse resp = client.execute(url);
      System.out.println(EntityUtils.toString(resp.getEntity()));
    } catch (IOException ex) {
      System.out.println("HTTP Request failed");
    }
  }
}