plugins {
    id("io.papermc.paperweight.userdev")
    id("java")
}

group = "de.placeblock"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    paperweight.paperDevBundle("1.21.9-R0.1-SNAPSHOT")
}