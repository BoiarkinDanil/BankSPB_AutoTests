plugins {
    java
    id("io.freefair.lombok") version "8.6"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.github.bonigarcia:webdrivermanager:5.6.3")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.31.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("io.cucumber:cucumber-java:7.18.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")
    testImplementation("org.junit.platform:junit-platform-suite:1.10.0")
    testImplementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("org.assertj:assertj-core:3.24.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.filter.tags", System.getProperty("cucumber.filter.tags", ""))
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}