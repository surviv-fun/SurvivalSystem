dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.luckperms)
    compileOnly(libs.kyori)

    implementation(libs.gson)
    implementation(libs.jedis)
    implementation(libs.mongo)

    implementation(project(":SurvivalSystem-Nms"))
}
