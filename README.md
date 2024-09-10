# Sample Codemod

This page holds an example Codemodder codemod, built with Gradle, as discussed in the [codemodder.io tutorial](http://codemodder.io/languages/java/fork_sample).

The newer methods in `java.nio.file.Files` replaced the need for some of community-loved APIs in `org.apache.commons.io.FileUtils`. and this project demonstrates creating a codemod to move `org.apache.commons.io.FileUtils#readLines()` to `java.nio.file.Files.readAllLines()`. The codemod should make changes like this:

```diff
- import org.apache.commons.io.FileUtils; // remove the import if possible
+ import java.nio.file.Files;

...

- List<String> lines = FilesUtils.readLines(file);
+ List<String> lines = Files.readAllLines(file.toPath());
```

# Setup

1. Install JDK 17 for building this project. We recommend [Eclipse Adoptium](https://adoptium.net/)

1. Install [Semgrep](https://semgrep.dev/) CLI. See
   [here](https://semgrep.dev/docs/getting-started/#installing-and-running-semgrep-locally)
   for instructions. It can usually be done via `pip`:
   ```shell
   pip install semgrep
   ```

If your Python library paths contain your home directory as a root folder (i.e.
due to the use of the `$HOME` environment variable), you may need to manually
set up your `PYTHONPATH` for tests:

```shell
PYTHONPATH=$HOME/<subpath-to-python-libs-folder> ./gradlew check
```

You can check your python paths with:

```shell
python -m site
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
