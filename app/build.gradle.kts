plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.androidmapkit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.androidmapkit"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
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

//    implementation ("com.yandex.android:maps.mobile:4.8.1-navikit")
////    implementation ("com.google.android.gms:play-services-location:21.3.0")
//    implementation ("com.yandex.mapkit.styling:automotivenavigation:4.8.1")
//    implementation ("com.yandex.mapkit.styling:roadevents:4.8.1")

    implementation(libs.maps.mobile)
//    implementation ("com.yandex.android:maps.mobile:4.3.1-full")

}