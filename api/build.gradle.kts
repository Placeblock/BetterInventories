import io.papermc.paperweight.util.path

plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
    id("maven-publish")
    signing
}

group = "de.codelix"
version = "2.2.2"

var artifactID = "BetterInventories"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":nms"))
    implementation(project(":v1_20_R1"))
    implementation(project(":craftbukkit"))

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.21:3.133.2")
}

paperweight {
    addServerDependencyTo = configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME).map { setOf(it) }
}

java {
    withJavadocJar()
    withSourcesJar()
    // Configure the java toolchain. This allows gradle to auto-provision JDK 21 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

signing {
    sign(publishing.publications)
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
        setFinalizedBy(listOf(reobfJar))
    }

    test {
        useJUnitPlatform()
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(21)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        title = "BetterInventories API Documentation"
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("${project.rootProject.layout.buildDirectory.path}/libs/plugin/${project.rootProject.name}-${project.version}.jar"))
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

