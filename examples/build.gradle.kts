plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.3.5"
}

group = "de.placeblock"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    paperDevBundle("1.20.1-R0.1-SNAPSHOT")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
}