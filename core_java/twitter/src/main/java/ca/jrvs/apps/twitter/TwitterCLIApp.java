package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonUtil;
import java.util.Arrays;

public class TwitterCLIApp {

  private TwitterController controller;

  public TwitterCLIApp() {
    controller = new TwitterController();
  }

  public void run(String command, String[] args) {
    switch (command) {
      case "post":
        postTweet(args);
        break;
      case "show":
        showTweet(args);
        break;
      case "delete":
        deleteTweet(args);
        break;
    }
  }

  private void postTweet(String[] args) {
    prettyPrint(controller.postTweet(args));
  }

  private void showTweet(String[] args) {
    prettyPrint(controller.showTweet(args));
  }

  private void deleteTweet(String[] args) {
    controller.deleteTweet(args).forEach(this::prettyPrint);
  }

  private void prettyPrint(Tweet tweet) {
    System.out.println(JsonUtil.toPrettyJson(tweet));
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException("Not enough arguments.\n"
          + "Expected: TwitterCLIApp post|show|delete [arguments]");
    } else {
      String command = args[0].toLowerCase();
      String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
      new TwitterCLIApp().run(command, commandArgs);
    }
  }
}