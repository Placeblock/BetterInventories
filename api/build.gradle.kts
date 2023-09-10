plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("maven-publish")
    signing
}

group = "de.codelix"
version = "1.3.6c-SNAPSHOT"

var isReleaseVersion = !version.toString().endsWith("SNAPSHOT")
var artifactID = "BetterInventories"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")


    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.9.0")
}

configurations.testImplementation {
    exclude(group = "io.papermc.paper", module = "paper-server")
}

java {
    withJavadocJar()
    withSourcesJar()
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

signing {
    sign(publishing.publications)
}

tasks {
    jar {
        setFinalizedBy(listOf(reobfJar))
    }

    test {
        useJUnitPlatform()
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        title = "BetterInventories API Documentation"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = artifactID
            version = project.version.toString()
            artifact(tasks["jar"]) {
                classifier=""
            }
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                packaging = "jar"
                name.set("BetterInventories")
                description.set("Easy to use and extensive InventoryAPI for Spigot")
                url.set("https://github.com/Placeblock/BetterInventories")
                licenses {
                    license {
                        name.set("GNU General Public License, Version 3")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }
                developers {
                    developer {
                        name.set("Felix")
                        organization.set("Codelix")
                        organizationUrl.set("https://codelix.de/")
                    }
                }
                scm {
                    url.set(
                        "https://github.com/Placeblock/BetterInventories.git"
                    )
                    connection.set(
                        "scm:git:git://github.com/Placeblock/BetterInventories.git"
                    )
                    developerConnection.set(
                        "scm:git:git://github.com/Placeblock/BetterInventories.git"
                    )
                }
                issueManagement {
                    url.set("https://github.com/Placeblock/BetterInventories/issues")
                }
            }
        }
    }
}

