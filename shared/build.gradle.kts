

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
    //id("dev.icerock.mobile.multiplatform-resources")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
    namespace = "co.touchlab.kampkit"
}

version = "1.2"

kotlin {
    android()
    ios()
    // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.time.ExperimentalTime")
            }
        }

        val commonMain by getting {
            dependencies {
                // Compose Multiplatform start
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // TODO: check this preview lib is failing
                //implementation(compose.preview)

                // Compose Multiplatform end
                //implementation("media.kamel:kamel-image:0.6.0")

                // moko resource
                //implementation("dev.icerock.moko:resources:0.23.0")
                //implementation("dev.icerock.moko:resources-compose:0.23.0")

                implementation(libs.koin.core)
                implementation(libs.coroutines.core)
                implementation(libs.sqlDelight.coroutinesExt)
                implementation(libs.bundles.ktor.common)
                implementation(libs.multiplatformSettings.common)
                implementation(libs.kotlinx.dateTime)
                api(libs.touchlab.kermit)
                api(libs.moko.core) // only ViewModel, EventsDispatcher, Dispatchers.UI
                api(libs.moko.compose) // api mvvm-core, getViewModel for Compose Multiplatfrom
            }

            // multiplatformResources {
            //     multiplatformResourcesPackage = "org.example.library" // required
            //     multiplatformResourcesClassName = "SharedRes" // optional, default MR
            //     multiplatformResourcesVisibility = MRVisibility.Internal // optional, default Public
            //     iosBaseLocalizationRegion = "en" // optional, default "en"
            //     multiplatformResourcesSourceSet = "commonClientMain"  // optional, default "commonMain"
            // }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.shared.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.sqlDelight.android)
                implementation(libs.ktor.client.okHttp)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.bundles.shared.androidTest)
            }
        }
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.sqlDelight.native)
                implementation(libs.ktor.client.ios)
                api(libs.touchlab.kermit.simple)
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
            linkerOpts("-lsqlite3")
            export(libs.touchlab.kermit.simple)
        }
        ios.deploymentTarget = "12.4"
        podfile = project.file("../ios/Podfile")
    }
}

sqldelight {
    databases.create("KaMPKitDb") {
        packageName.set("co.touchlab.kampkit.db")
    }
}
