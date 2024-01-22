plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization").version("1.9.10")
    id("com.google.devtools.ksp")
}

android {
    namespace = "app.maigret"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.maigret"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

val ktorVersion = "2.3.7"
val roomVersion = "2.6.1"

dependencies {
    // Ktor Client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    // Ktor Serializer (JSON)
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    // Room Persistence Library (SQLite ORM)
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    // (Optional) for Room
    implementation("androidx.room:room-ktx:$roomVersion") // Kotlin Extensions and Coroutines support
    implementation("androidx.room:room-rxjava2:$roomVersion") // RxJava2 support
    implementation("androidx.room:room-rxjava3:$roomVersion") // RxJava3 support
    implementation("androidx.room:room-guava:$roomVersion") // Guava support, including Optional and ListenableFuture
    implementation("androidx.room:room-paging:$roomVersion") // Paging 3 Integration
    // Other
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Add ability to call KFunction
    implementation(kotlin("reflect"))
}