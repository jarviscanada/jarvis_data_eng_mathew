package ca.jrvs.apps.trading.utils;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtils {

  /**
   * Maps exceptions to response codes. IllegalArgumentException -> 400 BAD_REQUEST
   * IllegalStateException -> 403 FORBIDDEN EntityNotFoundException -> 404 NOT_FOUND Others -> 500
   * INTERNAL_SERVER_ERROR
   *
   * @param ex The exception to map to an HTTP response code
   * @return an Exception with an HTTP response code attached
   */
  public static ResponseStatusException getResponseStatusException(Exception ex) {
    if (ex instanceof IllegalArgumentException) {
      return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } else if (ex instanceof IllegalStateException) {
      return new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
    } else if (ex instanceof EntityNotFoundException) {
      return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    } else {
      return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal error. Please contact admin.");
    }
  }
}
