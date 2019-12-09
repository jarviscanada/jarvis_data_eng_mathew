package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java regex scanner. Recursively scans the given directory, looking for filenames which match
 * the supplied regex. The results of the scan are written to the supplied output file.
 */
public class JavaGrepLambdaImp extends JavaGrepImp {

  private Logger jgrepLogger;

  /**
   * JavaGrepImp class constructor.
   */
  public JavaGrepLambdaImp() {
    jgrepLogger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);
  }

  /**
   * Overrides JavaGrepImp.readLines(). This implementation uses java.nio.Files.
   * @param file a File object which lines must be read from. This must be a file, not a directory
   * @return a List of Strings containing all readable lines of the supplied file
   */
  @Override
  public List<String> readLines(File file) {
    List<String> lines = new ArrayList<>();
    try {
      lines = Files.readAllLines(file.toPath()); // Ed may not consider this "using streams"
    } catch (IOException ex) {
      System.err.println("Couldn't read file " + file.getName());
      jgrepLogger.error(ex.getMessage());
    }
    return lines;
  }

  /**
   * Override of JavaGrepImp.listFiles(). Implemented using java.nio, streams, and lambdas
   * @param rootDir input directory
   * @return A list of files found in the search root
   */
  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();
    try {
      files = Files.walk(Paths.get(rootDir)).filter(x -> Files.isRegularFile(x))
          .map(Path::toFile).collect(Collectors.toList());
    } catch (IOException ex) {
      jgrepLogger.error(ex.getMessage());
    }
    return files;
  }

  /**
   * Main function. Checks to make sure only 3 arguments are given
   *
   * @param args Invocation arguments. Should be regex, scan_directory, output_file
   */
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger("Main");
    if (args.length != 3) {
      throw new IllegalArgumentException("Incorrect number of arguments\n"
          + "Usage: regex searchRoot outputFile");
    }
    JavaGrepLambdaImp jgrep = new JavaGrepLambdaImp();
    jgrep.setRegex(args[0]);
    jgrep.setRootPath(args[1]);
    jgrep.setOutFile(args[2]);
    try {
      jgrep.process();
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
  }
}