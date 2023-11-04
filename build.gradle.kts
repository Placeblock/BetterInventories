group = "de.codelix"


plugins {
    `java-library`
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("io.papermc.paperweight.userdev") version "1.5.8" apply false
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            useStaging.set(provider {
                !project(":api").version.toString().endsWith("-SNAPSHOT")
            })
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
