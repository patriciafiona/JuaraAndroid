plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.patriciafiona.learningforkids"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.patriciafiona.learningforkids"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.navigation:navigation-compose:2.7.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha10")
    implementation ("androidx.compose.material:material-icons-extended:1.5.4")

    implementation ("com.google.accompanist:accompanist-drawablepainter:0.25.1")
    implementation ("com.google.accompanist:accompanist-permissions:0.29.0-alpha")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    //Youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")

    //Firebase
    implementation("com.google.firebase:firebase-storage:20.3.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    //Coil
    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-compose:2.2.2")

    //Lottie - GIF Animation
    implementation("com.airbnb.android:lottie-compose:5.0.3")

    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.4")

    debugImplementation ("androidx.customview:customview:1.2.0-alpha02")
    debugImplementation ("androidx.customview:customview-poolingcontainer:1.0.0")

    //Adaptive layout
    implementation ("androidx.compose.material3:material3-window-size-class:1.2.0-alpha10")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}