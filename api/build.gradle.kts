plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("maven-publish")
}

group = "de.placeblock"
version = "1.3.4"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.20.1-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

java {
    withJavadocJar()
    withSourcesJar()
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
        title = "BetterInventories API Documentation"
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String?
            artifactId = "betterinventories"
            version = project.version as String?

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "PlaceblockRelease"
            url = uri("https://repo.codelix.de/releases")
            credentials{
                username = project.properties["placerepo.username"] as String?
                password = project.properties["placerepo.token"] as String?
            }
        }
        maven {
            name = "PlaceblockSnapshot"
            url = uri("https://repo.codelix.de/snapshots")
            credentials{
                username = project.properties["placerepo.username"] as String?
                password = project.properties["placerepo.token"] as String?
            }
        }
        maven {
            name = "PlaceblockPrivate"
            url = uri("https://repo.codelix.de/private")
            isAllowInsecureProtocol = true
            credentials{
                username = project.properties["placerepo.username"] as String?
                password = project.properties["placerepo.token"] as String?
            }
        }
    }
}