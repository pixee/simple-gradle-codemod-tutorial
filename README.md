# Sample Codemod

This page holds a Gradle codemod project discussed in the [codemodder.io tutorial](https://codemodder.io/java/).

The newer methods in `java.nio.file.Files` replaced the need for some of community-loved APIs in `org.apache.commons.io.FileUtils`. and this project demonstrates creating a codemod to move `org.apache.commons.io.FileUtils#readLines()` to `java.nio.file.Files.readAllLines()`. The codemod should make changes like this:

```diff
- import org.apache.commons.io.FileUtils; // remove the import if possible
+ import java.nio.file.Files;

...

- List<String> lines = FilesUtils.readLines(file);
+ List<String> lines = Files.readAllLines(file.toPath());
```

# Building & Testing

```bash
$ ./gradlew assemble
```

# Running

If you have some sample code you want to run this on:
```bash
$ ./gradle distZip

# do it without making the actual code changes on disk
$ sh build/distributions/nio-modernizer/nio-modernizer.sh --dry-run /my-project

# do it and make the actual code changes
$ sh build/distributions/nio-modernizer/nio-modernizer.sh /my-project
```
