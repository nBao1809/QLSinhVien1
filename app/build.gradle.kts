plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.qlsinhvien"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.qlsinhvien"
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
    packaging.resources {
        excludes.add("META-INF/NOTICE.md")
        excludes.add("META-INF/LICENSE.md")
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
    implementation(libs.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.lottie)
    implementation(libs.android.mail)
    implementation (libs.android.activation)


}
