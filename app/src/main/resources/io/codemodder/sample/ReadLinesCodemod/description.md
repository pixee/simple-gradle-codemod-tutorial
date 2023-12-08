This change modernizes an Apache Commons IO method to use Java NIO alternatives instead.

We prefer to use `java.nio` libraries instead of 3rd party to reduce our dependencies.

This change in particular translates Apache Commons IO `FileUtils#readLines()` to use Java NIO's `Files#readAllLines()`.