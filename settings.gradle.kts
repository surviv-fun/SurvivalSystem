rootProject.name = "SurvivalSystem"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

include(":core-nms", ":core", ":mob-mechanics", ":bosses", ":plugin")

project(":core-nms").name = "SurvivalSystem-Nms"
project(":core").name = "SurvivalSystem-Core"
project(":mob-mechanics").name = "SurvivalSystem-MobMechanics"
project(":bosses").name = "SurvivalSystem-Bosses"
project(":plugin").name = "SurvivalSystem-Bukkit"
