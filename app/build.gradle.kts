plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.rakamin.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.rakamin.newsapp"
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
    implementation("androidx.core:core-ktx:1.9.0") // Updated version
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // === DEPENDENCIES BERITA (RETROFIT, GLIDE, DLL) ===

    // Retrofit API Client
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Glide (image loader)
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // RecyclerView untuk list berita
    implementation("androidx.recyclerview:recyclerview:1.3.2") // Updated version

    // CardView untuk item berita
    implementation("androidx.cardview:cardview:1.0.0")

    // ConstraintLayout untuk layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ViewPager2 untuk headline news
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
