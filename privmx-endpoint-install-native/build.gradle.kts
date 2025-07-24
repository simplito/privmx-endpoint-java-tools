import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
    id("signing")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

//Apply script for publishing maven dependency
if (file("build-publish-maven.gradle.kts").exists()) {
    apply(from = project.file("build-publish-maven.gradle.kts"))
}

dependencies {
    implementation(gradleApi())
    compileOnly("com.android.tools.build:gradle:8.11.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
    testImplementation(libs.junit)
}

version = "2.0"
gradlePlugin {
    plugins {
        this.create("privmx-endpoint-install-native") {
            displayName = "privmx-endpoint-install-native"
            id = "com.simplito.privmx-endpoint-install-native"
            implementationClass =
                "com.simplito.tools.gradle.privmx_endpoint_install_native.PrivmxEndpointNativePlugin"
        }
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}