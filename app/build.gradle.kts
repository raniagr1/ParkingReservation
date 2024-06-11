plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.trying"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation ("com.google.code.gson:gson:2.8.5")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    //Coroutine tests
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    //MockWebserver
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.1")
    val room_version = "2.5.0"
    implementation ("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    testImplementation ("androidx.room:room-testing:$room_version")
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("com.google.firebase:firebase-auth:23.0.0") // Add Firebase Auth dependency
    implementation ("com.google.android.gms:play-services-auth:19.2.0") // Add Google Sign-In dependency
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.maps.android:android-maps-utils:2.2.0")
    implementation ("com.google.maps.android:maps-compose:2.0.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("com.lightspark:compose-qr-code:1.0.1")


    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    // notification permission
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
    implementation("androidx.datastore:datastore-preferences-android:1.1.1")

    // Declare dependency between tasks
    tasks.matching { it.name.startsWith("merge") && it.name.endsWith("Resources") }.configureEach {
        dependsOn("processDebugGoogleServices")

    }

}