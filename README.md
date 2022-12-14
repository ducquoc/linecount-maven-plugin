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

### Samples

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
Be aware of the version `1.4.8` must be existing in local repository (or downloadable from Internet repos).

##### examples-lc
There are 2 sub-folders: "java-dtest" and "jdownloader-dtest" in `examples-lc` folder, which are maven projects 
with plugins pre-configured in `pom.xml` (commented).

When the commented section in `pom.xml` are un-commented, those can be run at root project similar to above,
or can also be run mvn pointing to the pom file
```
  $ cd examples-lc
  $ ../mvnw net.ducquoc:linecount-maven-plugin:1.4.8:linecount -DpresentNonCode=true -f java-dtest/pom.xml
  $ cd jdownloader-dtest
  $ ../../mvnw net.ducquoc:linecount-maven-plugin:1.4.8:linecount -DpresentNonCode=true -f pom.xml
```

There are some parameters that can be configured in plugin configuration section in `pom.xml` , 
and can also be overriden by Java system property: `presentNonCode`, `display`, etc...
```
-DpresentNonCode=true -Ddisplay=true 
```

#### References

https://gitlab.com/ducquoc/linecount-maven-plugin

https://github.com/ducquoc/fresher-training

https://bitbucket.org/ducquoc/dq-training

https://ducquoc.github.io/

Gradle plugin (similarly): TBU (currently Groovy script is not running well with the plugin, not integrated well with CI/CD Jenkins, etc...)

