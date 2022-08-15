dependencies {
    compileOnly("org.spigotmc:spigot:${libs.versions.paper.get()}")
    implementation(project(":SurvivalSystem-Nms"))
    implementation(project(":SurvivalSystem-Core"))
}
