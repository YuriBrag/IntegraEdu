plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "yuri.bragine.integraedu"
    compileSdk = 35

    defaultConfig {
        applicationId = "yuri.bragine.integraedu"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ✅ Retrofit (HTTP client)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // ✅ Gson (JSON parser)
    implementation("com.google.code.gson:gson:2.8.8")

    // ✅ Retrofit + Gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
