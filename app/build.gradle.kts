plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.finalproject_hebaalsayyed_301357388"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalproject_hebaalsayyed_301357388"
        minSdk = 24
        targetSdk = 33
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
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.google.android.gms:play-services-maps:18.2.0")



    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //noinspection GradleDependency
    implementation ("com.squareup.retrofit2:converter-gson:2.8.1")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    kapt ("com.google.dagger:hilt-compiler:2.48")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")


    //noinspection GradleDependency
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    //noinspection GradleDependency
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.5")



    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")


    api ("com.serjltt.moshi:moshi-lazy-adapters:2.2")
    api ("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

}