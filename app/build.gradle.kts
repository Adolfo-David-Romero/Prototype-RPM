plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)


}

android {
    namespace = "sheridan.adolfo_david_romero.prototype_device"
    compileSdk = 34

    defaultConfig {
        applicationId = "sheridan.adolfo_david_romero.prototype_device"
        minSdk = 31
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    /* ExoPlayer modules */
    //https://developer.android.com/media/media3/exoplayer/hello-world#kts

    implementation(libs.androidx.media3.exoplayer.dash)
     //MediaItem --> https://developer.android.com/media/media3/exoplayer/rtsp

    //Maps
    implementation(libs.play.services.maps)
    implementation(libs.android.maps.ktx)
    implementation(libs.android.maps.compose)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    /*implementation(libs.media3.exoplayer.rtsp)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)*/
    implementation(libs.libvlc.all)
    implementation(libs.okhttp)
    implementation(libs.androidx.media)
    //implementation(libs.extension.rtsp)
    implementation(libs.exoplayer)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}