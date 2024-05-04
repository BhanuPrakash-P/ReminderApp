plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.bhanu.reminders"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bhanu.reminders"
        minSdk = 21
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
}

dependencies {
    // Only include one version of the androidx.core library
    implementation("androidx.core:core:1.13.0")

    // Only include one version of the androidx.versionedparcelable library
    implementation("androidx.versionedparcelable:versionedparcelable:1.1.1")

    //implementation ("com.android.support:support-compat:28.0.0")
//    implementation(libs.recyclerview)
//    implementation(libs.appcompat)
   implementation(libs.material)
//    implementation(libs.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}