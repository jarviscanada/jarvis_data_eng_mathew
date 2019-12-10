package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * return true if filename extension is jpg or jpeg (case insensitive)
   *
   * @param filename String name of a file to check
   * @return true if match found, otherwise false
   */
  boolean matchJpeg(String filename);

  /**
   * return true if ip is valid to simplify the problem, IP address range is from 0.0.0.0 to
   * 999.999.999.999
   *
   * @param ip String to check if it's the shape of an IP address
   * @return true if string is shaped like an IP address, otherwise false
   */
  boolean matchIp(String ip);

  /**
   * return true if line is empty (e.g. empty, white space, tabs, etc..)
   *
   * @param line String to check if empty
   * @return true if the String is empty, otherwise false
   */
  boolean isEmptyLine(String line);

}