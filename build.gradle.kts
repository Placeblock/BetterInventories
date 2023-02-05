plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("maven-publish")
}

group = "de.placeblock"
version = "1.1.3"

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
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    compileOnly("io.schark:ScharkDesign:1.5.0")

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
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String?
            artifactId = project.name
            version = project.version as String?

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "ScharkIO"
            url = uri("https://repo.schark.io/private")
            isAllowInsecureProtocol = true
            credentials{
                username = project.properties["reposilite.username"] as String?
                password = project.properties["reposilite.token"] as String?
            }
        }
    }
}