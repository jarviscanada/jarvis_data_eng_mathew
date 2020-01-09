package ca.jrvs.apps.trading.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/stable/");
    marketDataConfig.setToken(System.getenv("IEX_TOKEN"));
    return marketDataConfig;
  }

  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    return new PoolingHttpClientConnectionManager();
  }

  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl(System.getenv("PG_URL"));
    dataSource.setUsername(System.getenv("PG_USERNAME"));
    dataSource.setPassword(System.getenv("PG_PASSWORD"));
    return dataSource;
  }
}
