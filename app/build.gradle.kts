plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.sajon.dev.rickandmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sajon.dev.rickandmorty"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Jetpack Navigation
    val navigationVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Networking
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Moshi
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    // Skip this if you don't want to use integration libraries or configure Glide.
    annotationProcessor("androidx.annotation:annotation:1.7.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // Epoxy Airbnb
    val epoxyVersion = "5.1.1"
    implementation ("com.airbnb.android:epoxy:$epoxyVersion")
    annotationProcessor("com.airbnb.android:epoxy-processor:$epoxyVersion")
    implementation("com.airbnb.android:epoxy-paging:5.0.0")
    implementation("com.airbnb.android:epoxy-paging3:$epoxyVersion")

    // Paging
    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
}