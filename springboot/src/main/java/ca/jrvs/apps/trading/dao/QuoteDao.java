package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String DB_TABLE = "quote";
  private static final String SQL_QUOTE_SELECT =
      "SELECT * FROM " + DB_TABLE + " WHERE ticker = :ticker";
  private static final String SQL_QUOTE_SELECTALL = "SELECT * FROM " + DB_TABLE;
  private static final String SQL_QUOTE_COUNT = "SELECT COUNT(*) FROM " + DB_TABLE;
  private static final String SQL_QUOTE_UPDATE = "UPDATE " + DB_TABLE
      + " SET last_price = :last_price, bid_price = :bid_price, bid_size = :bid_size, "
      + "ask_price = :ask_price, ask_size = ask_size WHERE ticker = :ticker";
  private static final String SQL_QUOTE_DELETE =
      "DELETE FROM " + DB_TABLE + " WHERE ticker = :ticker";
  private static final String SQL_QUOTE_DELETEALL = "DELETE FROM " + DB_TABLE;

  private DataSource dataSource;
  private NamedParameterJdbcTemplate namedTemplate;
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  private Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  @Autowired
  public QuoteDao(DataSource dataSource) {
    this.dataSource = dataSource;
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(DB_TABLE);
  }

  /**
   * Writes a quote object to the underlying DataSource
   *
   * @param s   The quote to persist
   * @param <S> The type to write out
   * @return The quote that was saved
   */
  @Override
  public <S extends Quote> S save(S s) {
    int rowsModded;
    if (!existsById(s.getSymbol())) {
      rowsModded = simpleInsert.execute(s.getSqlValues());
    } else {
      rowsModded = namedTemplate.update(SQL_QUOTE_UPDATE, s.getSqlValues());
    }
    checkRowsModified(rowsModded);
    return s;
  }

  /**
   * Writes out multiple quotes to the underlying DataSource
   *
   * @param iterable the list of quotes to write out
   * @param <S>      The type of object to persist.
   * @return The saved quotes
   */
  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> iterable) {
    int rowsModded;
    for (S quote : iterable) {
      if (!existsById(quote.getSymbol())) {
        rowsModded = simpleInsert.execute(quote.getSqlValues());
      } else {
        rowsModded = namedTemplate.update(SQL_QUOTE_UPDATE, quote.getSqlValues());
      }
      checkRowsModified(rowsModded);
    }
    return iterable;
  }

  private void checkRowsModified(int modified) {
    if (modified != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to update data.", 1, modified);
    }
  }

  /**
   * Maybe returns a quote from the underlying DataSource based on the supplied Symbol
   *
   * @param symbol The Stock Market Symbol to search for
   * @return An Optional Quote for the Symbol specified
   */
  @Override
  public Optional<Quote> findById(String symbol) {
    return Optional.ofNullable(namedTemplate.queryForObject(
        SQL_QUOTE_SELECT, new MapSqlParameterSource("ticker", symbol), Quote.class));
  }

  /**
   * Checks if a Symbol exists in the quote table by querying for it.
   *
   * @param symbol A Ticker Symbol to check for
   * @return True if Symbol found, otherwise false
   */
  @Override
  public boolean existsById(String symbol) {
    return jdbcTemplate.queryForRowSet(SQL_QUOTE_SELECT).next();
  }

  /**
   * Finds all Ticker Symbol data in the database.
   *
   * @return An Iterable collection of Quotes, one for each Symbol in the DB
   */
  @Override
  public Iterable<Quote> findAll() {
    return jdbcTemplate.queryForList(SQL_QUOTE_SELECTALL, Quote.class);
  }

  /**
   * Get the number of symbols in the database
   *
   * @return the number of Symbols in the database
   */
  @Override
  public long count() {
    return jdbcTemplate.query(SQL_QUOTE_COUNT, new SingleColumnRowMapper<Long>()).get(0);
  }

  /**
   * Remove a Symbol from the database.
   *
   * @param symbol The symbol to remove
   */
  @Override
  public void deleteById(String symbol) {
    int deletedRows = namedTemplate.update(SQL_QUOTE_DELETE,
        new MapSqlParameterSource("ticker", symbol));
    logger.debug("Deleted " + deletedRows + " from quote table");
  }

  /**
   * Remove all Symbols from the database.
   */
  @Override
  public void deleteAll() {
    jdbcTemplate.execute(SQL_QUOTE_DELETEALL);
  }

  @Deprecated
  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Deprecated
  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Deprecated
  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
