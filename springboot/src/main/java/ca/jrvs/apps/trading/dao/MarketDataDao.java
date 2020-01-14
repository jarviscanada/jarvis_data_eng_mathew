package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.utils.JsonUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_QUOTE = "stock/market/quote/?symbols=";
  private final String IEX_BATCH_URL;
  HttpClientConnectionManager connectionManager;
  private String token;
  private String baseURL;
  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  @Autowired
  public MarketDataDao(HttpClientConnectionManager connectionManager,
      MarketDataConfig marketDataConfig) {
    this.connectionManager = connectionManager;
    token = marketDataConfig.getToken();
    baseURL = marketDataConfig.getHost();
    IEX_BATCH_URL = baseURL + IEX_BATCH_QUOTE;
  }

  HttpResponse httpGet(String url) {
    try {
      return getHttpClient().execute(new HttpGet(url));
    } catch (IOException ioex) {
      logger.error("Could not get response from " + url + '\n' + ioex.getMessage());
    }
    return null;
  }

  CloseableHttpClient getHttpClient() {
    return HttpClients.custom().setConnectionManager(connectionManager)
        .setConnectionManagerShared(true).build();
  }

  private <T> Optional<T> parseResponse(HttpResponse response, Class<T> clazz) {
    if (response != null) {
      int responseCode = response.getStatusLine().getStatusCode();
      if (responseCode >= 200 && responseCode < 300) {
        try {
          return Optional.of(
              JsonUtils.parseJsonString(EntityUtils.toString(response.getEntity()), clazz)
          );
        } catch (IOException | IllegalArgumentException ex) {
          logger.error(MarkerFactory.getMarker("JSON_ERROR"), "Failed to parse response as "
              + clazz.getCanonicalName() + "\nReason: " + ex.getMessage());
        }
      }
    }
    return Optional.empty();
  }

  /**
   * Obtains a quote from IEX for the specified symbol
   *
   * @param s The symbol to request a quote of from IEX
   * @return An optional which may contain the requested Quote
   */
  @Override
  public Optional<IexQuote> findById(String s) {
    String url = baseURL + "stock/" + s + "/quote/?token=" + token;
    HttpResponse response = httpGet(url);
    return parseResponse(response, IexQuote.class);
  }

  /**
   * Obtains quotes for multiple symbols from IEX.
   *
   * @param iterable The list of symbols to request from IEX
   * @return A list of IEX Quotes for the requested symbols
   */
  @Override
  public Iterable<IexQuote> findAllById(Iterable<String> iterable) {
    String url = IEX_BATCH_URL;
    StringJoiner joiner = new StringJoiner(",");
    for (String s : iterable) {
      joiner.add(s);
    }
    url += joiner.toString() + "&token=" + token;
    HttpResponse response = httpGet(url);
    return Arrays.asList(parseResponse(response, IexQuote[].class).orElseThrow(
        () -> new IllegalArgumentException("One or more Symbols were not found on IEX")
    ));
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
