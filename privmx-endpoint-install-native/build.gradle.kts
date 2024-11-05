plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
    id("signing")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

//Apply script for publishing maven dependency
//if (file("build-publish-maven.gradle.kts").exists()) {
    apply(from = project.file("build-publish-maven.gradle.kts"))
//}
dependencies {
    implementation(gradleApi())
    implementation("de.undercouch:gradle-download-task:5.6.0")
    testImplementation(libs.junit)
}

version = "1.1"
gradlePlugin {
    plugins {
        this.create("privmx-endpoint-install-native") {
            displayName = "privmx-endpoint-install-native"
            id = "com.simplito.privmx-endpoint-install-native"
            implementationClass =
                "com.simplito.tools.gradle.privmx_endpoint_install_native.PrivmxEndpointInstallNativePlugin"
        }
    }
}

kotlin {
    jvmToolchain(11)
}