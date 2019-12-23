package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public interface HttpHelper {

  /**
   * Execute a HTTP Post call.
   * @param uri Resource to request
   * @return an HTTP response for the request
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a HTTP Get call.
   * @param uri Resource to Request
   * @return an HTTP response for the request
   */
  HttpResponse httpGet(URI uri);
}
