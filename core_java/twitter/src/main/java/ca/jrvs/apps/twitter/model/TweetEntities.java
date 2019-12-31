package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetEntities {

  @JsonProperty("hashtags")
  private Hashtag[] hashtags;
  @JsonProperty("user_mentions")
  private Mention[] mentions;

  public TweetEntities() {

  }

  public Hashtag[] getHashtags() {
    return hashtags;
  }

  public void setHashtags(Hashtag[] hashtags) {
    this.hashtags = hashtags;
  }

  public Mention[] getMentions() {
    return mentions;
  }

  public void setMentions(Mention[] mentions) {
    this.mentions = mentions;
  }
}
