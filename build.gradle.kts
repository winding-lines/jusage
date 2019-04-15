import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;

plugins {
    // Apply the java plugin to add support for Java
    java

    // Generate files for the IDE
    idea

    // Integrate the Graal VM for native compilation
    id("com.palantir.graal") version "0.3.0-20-g2be40b9"

    // Build as a custom jar
    id("com.github.johnrengelman.shadow") version "4.0.3"

    // Allow to build as a java application
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}


dependencies {
    // This dependency is found on compile classpath of this component and consumers.
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("com.netflix.devinsight.rewrite:rewrite-core:1.2.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("commons-cli:commons-cli:1.3.1")
    compile(files("${System.getProperty("java.home")}/../lib/tools.jar"))
    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

application {
    // Define the main class for the application
    mainClassName = "com.windinglines.jusage.App"
}

graal {
    mainClass("com.windinglines.jusage.SimpleApp")
    outputName("jusage")
    graalVersion("1.0.0-rc15")
}

tasks.withType<ShadowJar> {
    baseName = "jusage"
    classifier = ""
    version = ""
}
