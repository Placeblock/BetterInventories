plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
}

group = "de.placeblock"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":nms"))
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}