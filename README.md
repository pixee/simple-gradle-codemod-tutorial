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

# Testing

```bash
$ ./gradlew check
```

# Building

```bash
$ ./gradlew distZip
```

# Running

After building, you can run the distribution packaged in the `distZip` task.

```bash
$ cd app/build/distributions/
$ unzip app.zip
 
# do it without making the actual code changes on disk
$ app/bin/app --dry-run /my-project

# do it and make the actual code changes
$ app/bin/app /my-project
```
