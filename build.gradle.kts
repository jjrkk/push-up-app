plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}
// The project lives in a Google Drive–synced folder (see docs/DECISIONS.md).
// Send every module's build output to a local folder outside Drive so sync never touches it.
val localBuildRoot = File(System.getProperty("user.home"), ".pushups-build")
allprojects {
    val dirName = if (this == rootProject) "_root" else path.removePrefix(":").replace(':', '/')
    layout.buildDirectory.set(localBuildRoot.resolve(dirName))
}
