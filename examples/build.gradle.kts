plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
}

group = "de.codelix"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    paperDevBundle("1.20.1-R0.1-SNAPSHOT")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
}