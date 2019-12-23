package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonUtil;
import java.util.Arrays;

public class TwitterCliApp {

  private Controller controller;

  public TwitterCliApp() {
    controller = new TwitterController();
  }

  public TwitterCliApp(Controller twitterController) {
    controller = twitterController;
  }

  /**
   * Main driver method of the TwitterCLIApp. Calls the controller based on the command received and
   * pretty-prints the results as a JSON document
   *
   * @param args    CLI Args
   */
  public void run(String[] args) {

    String command;
    String[] cmdArgs;

    if (args.length < 2) {
      throw new IllegalArgumentException("Not enough arguments.\n"
          + "Usage: TwitterCLIApp post | show | delete [arguments]");
    } else {
      command = args[0].toLowerCase();
      cmdArgs = Arrays.copyOfRange(args, 1, args.length);
    }

    switch (command) {
      case "post":
        postTweet(cmdArgs);
        break;
      case "show":
        showTweet(cmdArgs);
        break;
      case "delete":
        deleteTweet(cmdArgs);
        break;
      default:
        throw new IllegalArgumentException("Command not recognized: " + command + "\nUse one of:"
            + "post | show | delete");
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

}