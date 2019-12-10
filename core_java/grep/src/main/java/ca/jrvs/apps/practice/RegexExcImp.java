package ca.jrvs.apps.practice;

import org.slf4j.Logger;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class RegexExcImp implements RegexExc {

  public RegexExcImp (){}

  /**
   * returns true if filename extension is jpg or jpeg (case insensitive)
   *
   * @param filename String name of a file to check
   * @return true if match found, otherwise false
   */
  @Override
  public boolean matchJpeg(String filename){

    Pattern jpegpattern = Pattern.compile("^\\w+\\.(jpg|jpeg)$");
    return jpegpattern.matcher(filename).matches();
  }

  /**
   * return true if ip is valid to simplify the problem, IP address range is from 0.0.0.0 to
   * 999.999.999.999
   *
   * @param ip String to check if it's the shape of an IP address
   * @return true if string is shaped like an IP address, otherwise false
   */
  @Override
  public boolean matchIp(String ip) {

    Pattern ippattern = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
    return ippattern.matcher(ip).matches();
  }

  /**
   * return true if line is empty (e.g. empty, white space, tabs, etc..)
   *
   * @param line String to check if empty
   * @return true if the String is empty, otherwise false
   */
  @Override
  public boolean isEmptyLine(String line){
    Pattern emptylinepattern = Pattern.compile("^\\s*$");
    return emptylinepattern.matcher(line).matches();
  }

  public static void main(String[] args){
    RegexExcImp regeximp = new RegexExcImp();
    Logger logger = LoggerFactory.getLogger("");
    logger.info("Match JPEG 'somefile.jpg' " + regeximp.matchJpeg("somefile.jpg"));
    logger.info("Match JPEG 'somefile.jpeg' " + regeximp.matchJpeg("somefile.jpeg"));
    logger.info("Match JPEG 'somefile.jpp' " + regeximp.matchJpeg("somefile.jpp"));
    logger.info("Match JPEG '.jpg' " + regeximp.matchJpeg(".jpg"));

    logger.info("Match IP '0.0.0.0' " + regeximp.matchIp("0.0.0.0"));
    logger.info("Match IP '0,0,0,0' " + regeximp.matchIp("0,0,0,0"));
    logger.info("Match IP '255.255.255.255' " + regeximp.matchIp("255.255.255.255"));
    logger.info("Match IP '0.00.000.0000' " + regeximp.matchIp("0.00.000.0000"));
    logger.info("Match IP '192.168.0.F' " + regeximp.matchIp("192.168.0.F"));

    logger.info("Match empty line '' " + regeximp.isEmptyLine(""));
    logger.info("Match empty line 'aaa' " + regeximp.isEmptyLine("aaa"));
    logger.info("Match empty line '\\n' " + regeximp.isEmptyLine("\n"));
  }
}
