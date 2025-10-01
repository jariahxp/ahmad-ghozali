import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // ⬅️ Wajib untuk Kotlin 2.0+

}

android {
    namespace = "com.example.ahmad_ghozali"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ahmad_ghozali"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
// Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
// ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
// Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
// Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
// Coil
    implementation(libs.coil.compose)
// Logging
    implementation(libs.okhttp.logging)
    implementation ("androidx.compose.material:material-icons-extended:1.5.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    implementation(platform("androidx.compose:compose-bom:2025.01.00"))
    implementation ("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.core:core-splashscreen:1.0.1")
}