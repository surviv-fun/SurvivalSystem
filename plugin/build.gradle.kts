import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.luckperms)
    compileOnly(libs.kyori)

    implementation(project(":SurvivalSystem-Nms"))
    implementation(project(":SurvivalSystem-Core"))
    implementation(project(":SurvivalSystem-MobMechanics"))
    implementation(project(":SurvivalSystem-Bosses"))
}

tasks.named<ShadowJar>("shadowJar") {
    // Get rid of all the libs which are 100% unused.
    minimize()
    mergeServiceFiles()
}
