package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonUtil;
import java.util.Arrays;

public class TwitterCliApp {

  private TwitterController controller;

  public TwitterCliApp() {
    controller = new TwitterController();
  }

  /**
   * Main driver method of the TwitterCLIApp. Calls the controller based on the command received and
   * pretty-prints the results as a JSON document
   *
   * @param command The command the app should perform, post | show | delete
   * @param args    The arguments to be used with the command.
   */
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
      default:
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
          + "Usage: TwitterCLIApp post|show|delete [arguments]");
    } else {
      String command = args[0].toLowerCase();
      String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
      new TwitterCliApp().run(command, commandArgs);
    }
  }
}