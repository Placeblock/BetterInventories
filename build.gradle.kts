plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.3.5"
}

group = "de.placeblock"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://repo.schark.io/private")
        credentials{
            username = project.properties["reposilite.username"] as String?
            password = project.properties["reposilite.token"] as String?
        }
    }
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    paperDevBundle("1.19.3-R0.1-SNAPSHOT")

    compileOnly("io.schark:ScharkDesign:1.4.2a")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    test {
        useJUnitPlatform()
    }
}