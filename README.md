This project is mostly a proof of concept around building native code with graalvm.

## Description

Native java binary to search for usages of a certain package across many projects. 
All these projects are located in one base folder.

## Building


You need gradle 5.3 and java 8 for building. 
You can use [sdkman!](https://sdkman.io) to install them.


Once your dependencies are installed build using

```gradle nativeImage```

This uses the Palantir graal plugin to automatically download the Graal VM.

## Running

```build/graal/jusage package-name some-folder```

Then see jusage-reference.json in some-folder.

## Development

Build Intellij project files with 

```gradle idea```


The native compilation does not seem to work well with Java reflection or JDBC. 
The one alternative that works is to use [Gson](https://github.com/google/gson/blob/master/UserGuide.md)
with explicit serializers.

