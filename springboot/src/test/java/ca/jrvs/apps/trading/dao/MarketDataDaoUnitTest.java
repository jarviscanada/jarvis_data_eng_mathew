package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MarketDataDaoUnitTest {

  MarketDataConfig testConfig = new MarketDataConfig();
  @Spy
  MarketDataDao spyDao = new MarketDataDao(new PoolingHttpClientConnectionManager(), testConfig);
  @Mock
  CloseableHttpClient mockClient;
  @Mock
  CloseableHttpResponse mockResponse;
  @Mock
  StatusLine mockStatus;

  String jsonString = "{\n" + "\"symbol\": \"GOOG\",\n" + "\"companyName\": \"Alphabet, Inc.\",\n"
      + "\"primaryExchange\": \"NASDAQ\",\n" + "\"calculationPrice\": \"tops\",\n"
      + "\"open\": null,\n" + "\"openTime\": null,\n" + "\"close\": null,\n"
      + "\"closeTime\": null,\n" + "\"high\": null,\n" + "\"low\": null,\n"
      + "\"latestPrice\": 1397.94,\n" + "\"latestSource\": \"IEX real time price\",\n"
      + "\"latestTime\": \"12:22:25 PM\",\n" + "\"latestUpdate\": 1578417745637,\n"
      + "\"latestVolume\": null,\n" + "\"iexRealtimePrice\": 1397.94,\n"
      + "\"iexRealtimeSize\": 100,\n" + "\"iexLastUpdated\": 1578417745637,\n"
      + "\"delayedPrice\": null,\n" + "\"delayedPriceTime\": null,\n"
      + "\"extendedPrice\": null,\n" + "\"extendedChange\": null,\n"
      + "\"extendedChangePercent\": null,\n" + "\"extendedPriceTime\": null,\n"
      + "\"previousClose\": 1394.21,\n" + "\"previousVolume\": 1733149,\n"
      + "\"change\": 3.73,\n" + "\"changePercent\": 0.00268,\n"
      + "\"volume\": null,\n" + "\"iexMarketPercent\": 0.034435978413454964,\n"
      + "\"iexVolume\": 21906,\n" + "\"avgTotalVolume\": 1267540,\n"
      + "\"iexBidPrice\": 0,\n" + "\"iexBidSize\": 0,\n"
      + "\"iexAskPrice\": 0,\n" + "\"iexAskSize\": 0,\n"
      + "\"marketCap\": 964191767635,\n" + "\"peRatio\": 29.76,\n"
      + "\"week52High\": 1397.94,\n" + "\"week52Low\": 1025,\n"
      + "\"ytdChange\": 0.022309000000000002,\n" + "\"lastTradeTime\": 1578417745637,\n"
      + "\"isUSMarketOpen\": true\n" + "}";

  String jsonStringArray = "[{\n" + "\"symbol\": \"GOOG\",\n" + "\"companyName\": \"Alphabet, Inc.\",\n"
      + "\"primaryExchange\": \"NASDAQ\",\n" + "\"calculationPrice\": \"tops\",\n"
      + "\"open\": null,\n" + "\"openTime\": null,\n" + "\"close\": null,\n"
      + "\"closeTime\": null,\n" + "\"high\": null,\n" + "\"low\": null,\n"
      + "\"latestPrice\": 1397.94,\n" + "\"latestSource\": \"IEX real time price\",\n"
      + "\"latestTime\": \"12:22:25 PM\",\n" + "\"latestUpdate\": 1578417745637,\n"
      + "\"latestVolume\": null,\n" + "\"iexRealtimePrice\": 1397.94,\n"
      + "\"iexRealtimeSize\": 100,\n" + "\"iexLastUpdated\": 1578417745637,\n"
      + "\"delayedPrice\": null,\n" + "\"delayedPriceTime\": null,\n"
      + "\"extendedPrice\": null,\n" + "\"extendedChange\": null,\n"
      + "\"extendedChangePercent\": null,\n" + "\"extendedPriceTime\": null,\n"
      + "\"previousClose\": 1394.21,\n" + "\"previousVolume\": 1733149,\n"
      + "\"change\": 3.73,\n" + "\"changePercent\": 0.00268,\n"
      + "\"volume\": null,\n" + "\"iexMarketPercent\": 0.034435978413454964,\n"
      + "\"iexVolume\": 21906,\n" + "\"avgTotalVolume\": 1267540,\n"
      + "\"iexBidPrice\": 0,\n" + "\"iexBidSize\": 0,\n"
      + "\"iexAskPrice\": 0,\n" + "\"iexAskSize\": 0,\n"
      + "\"marketCap\": 964191767635,\n" + "\"peRatio\": 29.76,\n"
      + "\"week52High\": 1397.94,\n" + "\"week52Low\": 1025,\n"
      + "\"ytdChange\": 0.022309000000000002,\n" + "\"lastTradeTime\": 1578417745637,\n"
      + "\"isUSMarketOpen\": true\n" + "}]";

  @Before
  public void testInit(){
    doReturn(mockResponse).when(spyDao).httpGet(any());
    when(mockResponse.getStatusLine()).thenReturn(mockStatus);
    when(mockStatus.getStatusCode()).thenReturn(200);
  }

  @Test
  public void findById() {
    try {
      when(mockResponse.getEntity()).thenReturn(new StringEntity(jsonString));
    } catch (UnsupportedEncodingException ueex) {
      System.out.println("help!");
    }
    IexQuote quote = spyDao.findById("goog").orElse(null);
    assertNotNull(quote);
    assertEquals("GOOG", quote.getSymbol());
  }

  @Test
  public void findAllById() {
    try {
      when(mockResponse.getEntity()).thenReturn(new StringEntity(jsonStringArray));
    } catch (UnsupportedEncodingException ueex) {
      System.out.println("help!");
    }
    List<String> symbols = Arrays.asList("aapl", "goog", "t");
    Iterable<IexQuote> quotes = spyDao.findAllById(symbols);
    for(IexQuote quote : quotes) {
      assertTrue(symbols.contains(quote.getSymbol().toLowerCase()));
    }
  }
}