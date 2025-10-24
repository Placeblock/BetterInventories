group = "de.codelix"


plugins {
    `java-library`
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19" apply false
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            useStaging.set(provider {
                !project(":api").version.toString().endsWith("-SNAPSHOT")
            })
        }
    }
}
