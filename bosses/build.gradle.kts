dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.luckperms)
    compileOnly(libs.kyori)

    implementation(project(":SurvivalSystem-Core"))
    implementation(project(":SurvivalSystem-MobMechanics"))
}
