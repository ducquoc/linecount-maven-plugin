linecount maven plugin
==========

Maven plugin to count number of lines in source code.


### Usage

**Basic Usage**

Build the project, install the artifact/plugin to local repository (or deploy to remote repository).
```bash
  $ mvnw clean install -DskipTests=true
```

See sample below.

#### Samples

Current version is 1.4.8

  $ ./mvnw clean install -DskipTests=true
```log
[INFO] --- maven-install-plugin:2.4:install (default-install) @ linecount-maven-plugin ---
[INFO] Installing /Users/ducquoc/OS/bb/dq-training/linecount-maven-plugin/target/linecount-maven-plugin-1.4.8.jar to /Users/ducquoc/.m2/repository/net/ducquoc/linecount-maven-plugin/1.4.8/linecount-maven-plugin-1.4.8.jar
[INFO] Installing /Users/ducquoc/OS/bb/dq-training/linecount-maven-plugin/pom.xml to /Users/ducquoc/.m2/repository/net/ducquoc/linecount-maven-plugin/1.4.8/linecount-maven-plugin-1.4.8.pom
[INFO] Installing /Users/ducquoc/OS/bb/dq-training/linecount-maven-plugin/target/linecount-maven-plugin-1.4.8-sources.jar to /Users/ducquoc/.m2/repository/net/ducquoc/linecount-maven-plugin/1.4.8/linecount-maven-plugin-1.4.8-sources.jar
```
Now that the plugin 1.4.8 can be found, we can run the plugin to count the number of lines in the project.

#### Running samples

Given the plugin exists (built and found in repo), plugin can be run on this project source code as well
```
  $ mvn net.ducquoc:linecount-maven-plugin:1.4.8:linecount -DpresentNonCode=true
  $ ./mvnw net.ducquoc:linecount-maven-plugin:linecount -DpresentNonCode=true
```

There are 2 sub-folders: "java-dtest" and "jdownloader-dtest", which are maven projects 
with plugins pre-configured in `pom.xml` (commented).

When the commented section in `pom.xml` are un-commented, those can be run at root project similar to above,
or can also be run mvn pointing to the pom file
```
  $ ./mvnw net.ducquoc:linecount-maven-plugin:1.4.8:linecount -DpresentNonCode=true -f jdownloader-dtest/pom.xml
  $ ./mvnw net.ducquoc:linecount-maven-plugin:1.4.8:linecount -DpresentNonCode=true -f java-dtest/pom.xml
```

#### References

https://bitbucket.org/ducquoc/dq-training

https://ducquoc.github.io/

Gradle plugin (similarly): TBU

