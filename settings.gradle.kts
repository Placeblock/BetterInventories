pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "BetterInventories"
include("api")
include("examples")
include("v1_20_R1")
include("nms")
