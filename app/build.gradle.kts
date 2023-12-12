plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.notessharingapp"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.notessharingapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
}



dependencies {

    implementation("com.google.android.material:material:1.10.0")

//    implementation ("com.android.support:support-compat:28.0.0")
//    implementation ("com.android.support:support-core-utils:26.1.0")

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")



    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // For Recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // for animation using lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    // duo navigation view
    implementation("nl.psdcompany:duo-navigation-drawer:3.0.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")

//    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Storage
    // Add the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-storage-ktx")

    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.firebase:firebase-firestore-ktx")

    // implementing event bus
    implementation("org.greenrobot:eventbus:3.3.1")

    // implementing glide extension
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // FireStore database
    implementation ("com.google.firebase:firebase-firestore:24.10.0")

    // adding RazorPay dependency
    implementation ("com.razorpay:checkout:1.6.33")


    // firebase realtime database
    implementation("com.google.firebase:firebase-database-ktx")

//    // for viewing pdf
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")

//    implementation ("com.github.delight-im:Android-AdvancedWebView:v3.2.1")


//    implementation ("com.android.support:support-annotations:28.0.0")

//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}