package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.GeoLoc;
import ca.jrvs.apps.twitter.model.Tweet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TwitterService contains validation and some additional processing of Tweets being made through
 * TwitterCLI.
 */
@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao<Tweet, Long> dao;

  //Default Constructor
  public TwitterService() {
    dao = new TwitterDao();
  }

  //Alternative constructor for providing a custom TwitterDao
  @Autowired
  public TwitterService(CrdDao<Tweet, Long> dao) {
    this.dao = dao;
  }

  /**
   * Validate and post a user input Tweet.
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) throws IllegalArgumentException {
    String tweetText = tweet.getText();
    GeoLoc coords = tweet.getLocation();
    float latitude;
    float longitude;
    boolean validCoords = false;
    boolean hasCoords = false;
    boolean validText;

    validText = validateText(tweetText);
    if (coords != null) {
      longitude = coords.getCoordinates()[0];
      latitude = coords.getCoordinates()[1];
      hasCoords = true;
      validCoords = validateCoordinates(latitude, longitude);
    }

    if (validText) {
      if (hasCoords && !validCoords) {
        throw new IllegalArgumentException("Coordinates included, but are invalid");
      } else {
        return dao.create(tweet);
      }
    }
    throw new IllegalArgumentException("Tweet text exceeds 280 characters");
  }

  private boolean validateText(String tweetText) {
    // Lazy length check. Twitter shortens URLs so actual tweet length may be shorter than this.
    // Due to UTF8 encoding quirks, Normalizing the tweet text may change its length, Twitter uses
    // NFC normalization before checking tweet length, so we do that too.
    tweetText = Normalizer.normalize(tweetText, Form.NFC);
    return tweetText.length() <= 280 && tweetText.length() > 0;
  }

  private boolean validateCoordinates(float latitude, float longitude) {
    // Geolocation check. Lat is +- 90, Long is +- 180
    return !(Math.abs(latitude) > 90) && !(Math.abs(longitude) > 180);
  }

  /**
   * Search a tweet by ID.
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields) {
    long longId;
    try {
      longId = Long.parseLong(id);
    } catch (NumberFormatException nfex) {
      throw new IllegalArgumentException(id + " is not a valid Tweet ID");
    }
    Tweet returnedTweet = dao.findById(longId);
    if (fields.length > 0 && fields[0] != null) {
      Tweet filteredTweet = new Tweet();
      Class<Tweet> metaTweet = Tweet.class;
      Method[] tweetMethods = metaTweet.getMethods();
      // Obtain the methods in the Tweet class, then check if each is a getter or setter for the
      // fields passed in. since the fields are private we can't access them directly via reflection
      for (String field : fields) {
        Method getter = null;
        Method setter = null;
        field = field.substring(0, 1).toUpperCase() + field.substring(1);
        for (Method m : tweetMethods) {
          if (m.getName().equals("get" + field)) {
            getter = m;
          }
          if (m.getName().equals("set" + field)) {
            setter = m;
          }
          if (getter != null && setter != null) {
            try {
              setter.invoke(filteredTweet, getter.invoke(returnedTweet));
              break;
            } catch (IllegalAccessException | InvocationTargetException ex) {
              throw new IllegalArgumentException("Getter/Setter could not be accessed");
            }
          }
        }
        if (getter == null || setter == null) {
          throw new IllegalArgumentException("The field " + field + " does not exist");
        }
      }
      return filteredTweet;
    }
    return returnedTweet;
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedTweets = new ArrayList<>();
    for (String id : ids) {
      long idNum;
      try {
        idNum = Long.parseLong(id);
      } catch (NumberFormatException nfex) {
        throw new IllegalArgumentException(id + " is not a valid Tweet ID.");
      }
      try {
        deletedTweets.add(dao.deleteById(idNum));
      } catch (IllegalArgumentException ignored) {
        // TwitterDao.deleteByID() throws IllegalArgumentException if we can't delete a tweet with
        // a given ID, we cant to continue attempting to delete tweets if this happens.
        // May add a logging line here later.
      }
    }
    return deletedTweets;
  }
}
