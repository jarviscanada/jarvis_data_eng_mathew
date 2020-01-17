package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract JdbcTemplate getJdbcTemplate();

  abstract SimpleJdbcInsert getSimpleJdbcInsert();

  abstract public String getTableName();

  abstract public String getIdColumnName();

  abstract Class<T> getEntityClass();

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation
   * might have changed the entity instance completely.
   *
   * @param entity must not be {@literal null}.
   * @return the saved entity will never be {@literal null}.
   */
  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateEntity(entity) != 1) {
        throw new DataRetrievalFailureException("Data update failed");
      }
    } else {
      addEntity(entity);
    }
    return entity;
  }

  private <S extends T> void addEntity(S entity) {
    entity.setId(getSimpleJdbcInsert().executeAndReturnKey(
        new BeanPropertySqlParameterSource(entity)).intValue());
  }

  abstract public int updateEntity(T entity);

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   * @return the saved entities will never be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    for (S entity : entities) {
      save(entity);
    }
    return entities;
  }

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public Optional<T> findById(Integer id) {
    String findQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    try {
      return Optional.ofNullable(getJdbcTemplate().queryForObject(findQuery,
          BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException ex) {
      logger.debug("Cannot find ID: " + id + " in table " + getTableName());
    }
    return Optional.empty();
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  @Override
  public boolean existsById(Integer id) {
    logger.info("Looking up entity with ID: " + id);
    String existsQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    List<T> results = getJdbcTemplate().queryForList(existsQuery, getEntityClass(), id);
    return results.size() > 0;
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Override
  public List<T> findAll() {
    String getAllQuery = "SELECT * FROM " + getTableName();
    return getJdbcTemplate().query(getAllQuery,
        BeanPropertyRowMapper.newInstance(getEntityClass()));
  }

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param ids The IDs to search
   * @return A list of Entities
   */
  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    List<T> entities = new ArrayList<>();
    for (int id : ids) {
      entities.add(findById(id).orElseThrow(IllegalArgumentException::new));
    }
    return entities;
  }

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  @Override
  public long count() {
    String countQuery = "SELECT COUNT(*) FROM " + getTableName();
    return getJdbcTemplate().query(countQuery, SingleColumnRowMapper.newInstance(Long.class))
        .get(0);
  }

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   */
  @Override
  public void deleteById(Integer id) {
    String deleteQuery = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    getJdbcTemplate().update(deleteQuery, id);
  }

  /**
   * Deletes a given entity.
   *
   * @param entity The entity to delete from the internal DB
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public void delete(T entity) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Deletes the given entities.
   *
   * @param entities Entities to delete from the internal DB
   * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
   */
  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {
    String deleteAllQuery = "DELETE FROM " + getTableName();
    getJdbcTemplate().execute(deleteAllQuery);
  }
}
