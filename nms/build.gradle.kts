plugins {
    id("java")
}

group = "de.placeblock"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT")
}