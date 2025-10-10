import java.util.Properties

val MAP_API_KEY_NAME = "MAP_API_KEY"

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.23"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.andrev133.photoalbummapapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.andrev133.photoalbummapapp"
        minSdk = 29
        targetSdk = 35
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
    buildTypes.forEach {
        it.buildConfigField(
            "String",
            MAP_API_KEY_NAME,
            getMapkitApiKey()
        )
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.1")
    ksp("com.google.dagger:hilt-compiler:2.56.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")

    // OkHttp3
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // MapKit
    implementation("ru.sulgik.mapkit:yandex-mapkit-kmp:0.1.0")
    implementation("ru.sulgik.mapkit:yandex-mapkit-kmp-compose:0.1.0")

    // Default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

fun getMapkitApiKey(): String {
    return try {
        val properties = Properties()
        rootProject.file("secret.properties").inputStream().use { properties.load(it) }
        val value = properties.getProperty(MAP_API_KEY_NAME, "")
        if (value.isEmpty()) {
            throw InvalidUserDataException("MapKit API key is not provided. Set your API key in the project's local.properties file: `MAPKIT_API_KEY=<your-api-key-value>`.")
        }
        value
    } catch (e: Exception) {
        project.findProperty(MAP_API_KEY_NAME) as String? ?: System.getenv()[MAP_API_KEY_NAME] ?: ""
    }
}