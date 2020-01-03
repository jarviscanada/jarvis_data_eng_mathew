# JavaGrep
## Introduction
Java Grep emulates some functionality of the Linux command line tool `grep`. 
When run it searches recursively through all the files in a directory for lines which match a given regular expression, and outputs those lines to a file.

This project served as an introduction to Java's Regex classes (Pattern and Matcher), Streams, Lambdas, and the java.nio library.

### Files
 - [JavaGrepImp.java](src/main/java/ca/jrvs/apps/grep/JavaGrepImp.java) uses standard java.io libraries to implement grep.
 - [JavaGrepLambdaImp.java](src/main/java/ca/jrvs/apps/grep/JavaGrepLambdaImp.java) uses java.nio and Streams to implement grep.
 - Several practice files may be found in src/main/java/ca/jrvs/apps/practice/

## Usage
`JavaGrepImp` and `JavaGrepLambdaImp` both take the same three arguments, `regex search_root output_file`.
### Sample usage
The following searches the files in the `src/` directory for lines starting with `public`, 
then writes those lines to `./jgrep.txt`. Cat is used to show sample output.
```shell script
JavaGrepImp.jar ^(public) ./src/ ./jgrep.txt
cat jgrep.txt
JavaGrep.java: public interface JavaGrep {
JavaGrepImp.java: public class JavaGrepImp implements JavaGrep { 
JavaGrepLambdaImp.java: public class JavaGrepLambdaImp extends JavaGrepImp {
...
```
## JavaGrep Pseudocode
The following is a high level description of how JavaGrep functions.
```
Recursively walk through the search root directory, get a list of files in it.
For each file found,
    For each line in the file,
        Test the line against the Regex pattern,
        If the  pattern is found, write the line to the output file.
```
## Possible Performance Issues
As performing disk I/O is relatively slow and linear searches are being performed on each line, 
This program may run slowly when processing large files. If such files contain structured data,
consider using a program which supports complex search algorithms.

## Possible Improvements
 - Support for search by filename with wildcards
 - Inclusion of line numbers in search results
 - Stream-based implementation may run better in a pure stream environment instead of 
 inheriting from the list-based implementation.
