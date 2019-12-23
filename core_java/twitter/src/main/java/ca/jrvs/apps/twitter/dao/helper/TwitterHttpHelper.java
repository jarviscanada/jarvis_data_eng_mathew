package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  private final String CONSUMER_KEY;
  private final String CONSUMER_SECRET;
  private final String ACCESS_KEY;
  private final String ACCESS_SECRET;
  private OAuthConsumer oac;
  private HttpClient client;
  private Logger httpLogger;

  // Default Constructor
  public TwitterHttpHelper() {
    CONSUMER_KEY = System.getenv("CONSUMER_TOKEN");
    CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
    ACCESS_KEY = System.getenv("ACCESS_TOKEN");
    ACCESS_SECRET = System.getenv("ACCESS_SECRET");
    httpLogger = LoggerFactory.getLogger(TwitterHttpHelper.class);
    oac = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    oac.setTokenWithSecret(ACCESS_KEY, ACCESS_SECRET);
    client = HttpClientBuilder.create().build();
  }

  /**
   * Constructor for TwitterHttpHelper. The OAuth keys must be supplied for Signpost
   *
   * @param consumerKey    The OAuth Consumer Key
   * @param consumerSecret The OAuth Consumer Secret
   * @param accessKey      The OAuth Access Key
   * @param accessSecret   The Oauth Access Secret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessKey,
      String accessSecret) {
    CONSUMER_KEY = consumerKey;
    CONSUMER_SECRET = consumerSecret;
    ACCESS_KEY = accessKey;
    ACCESS_SECRET = accessSecret;
    httpLogger = LoggerFactory.getLogger(TwitterHttpHelper.class);
    oac = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    oac.setTokenWithSecret(ACCESS_KEY, ACCESS_SECRET);
    client = HttpClientBuilder.create().build();
  }

  /**
   * Execute a HTTP Post call. Use this if the Post params are embbedded in the URL
   *
   * @param uri The resource to POST to
   * @return The HTTPResponse if received, otherwise null.
   */
  @Override
  public HttpResponse httpPost(URI uri) {
    HttpPost post = new HttpPost(uri);
    return getResponse(post);
  }

  /**
   * Execute an HTTP Get call.
   *
   * @param uri Remote resource to access
   * @return an HTTP Response for the given URI's request
   */
  @Override
  public HttpResponse httpGet(URI uri) {
    HttpGet get = new HttpGet(uri);
    return getResponse(get);
  }

  private HttpResponse getResponse(HttpUriRequest request) throws RuntimeException {
    HttpResponse response;
    oauthSign(request);
    try {
      response = client.execute(request);
    } catch (ClientProtocolException cpex) {
      httpLogger.error("HTTP returned an error\n" + cpex.getMessage());
      throw new RuntimeException();
    } catch (IOException ex) {
      httpLogger.error("Failed to read HTTP Response\n" + ex.getMessage());
      throw new RuntimeException();
    }
    return response;
  }

  private void oauthSign(HttpUriRequest request) throws RuntimeException {
    try {
      oac.sign(request);
    } catch (OAuthException oaex) {
      httpLogger.error("OAuth signing failed\n" + oaex.getMessage());
      throw new RuntimeException();
    }
  }
}
