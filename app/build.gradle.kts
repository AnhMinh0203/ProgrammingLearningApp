plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.programinglearningapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.programinglearningapp"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.annotation)
    implementation(libs.activity)
    // Thêm phụ thuộc Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Thêm thư viện RTE cho nội dung bài học
//    implementation(libs.android.rteditor)
    implementation (libs.richeditor.android)
//    implementation(libs.richeditor.compose)
    // Phụ thuộc cho unit test và UI test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}