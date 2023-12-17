plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
}

group = "de.codelix"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
}