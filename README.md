Grep like functionality for java projects. All the projects are assumed
to be located in the same base folder.

## Description

This project is mostly a proof of concept. It is using two technologies to build
self contained java applications:

- Native using [GraalVM](https://www.graalvm.org/)
- One bundler jar using [Shadow](https://github.com/johnrengelman/shadow)


You need gradle 5.3 and java 8 for building. 
You can use [sdkman!](https://sdkman.io) to install them.

## Native

Once your dependencies are installed build the native application using the `nativeImage` task.

```gradle nativeImage```

This uses Palantir's graal plugin to automatically download the Graal VM.

Execute the binary application in the `build/graal` output folder.

```build/graal/jusage package-name some-folder```

Then see jusage-reference.json in some-folder.

## Shadow

To build the shadow application use the `shadowJar` task.

```gradle shadowJar```

Execute the self contained jar in the `build/lib` folder.

```java -jar build/libs/jusage.jar package-name some-folder```


# Development

Build Intellij project files with 

```gradle idea```


The native compilation does not seem to work well with Java reflection or JDBC. 
The one alternative that works is to use [Gson](https://github.com/google/gson/blob/master/UserGuide.md)
with explicit serializers.

