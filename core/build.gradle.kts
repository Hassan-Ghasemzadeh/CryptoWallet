plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.kotlin.serializition)
    alias(libs.plugins.kotlin.parcelable)
}

android {
    namespace = "com.softwarecleandevelopment.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
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
}

hilt {
    enableAggregatingTask = false
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    //coroutine
    implementation(libs.androidx.coroutine.android)
    implementation(libs.androidx.coroutine.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //dagger-hilt
    implementation(libs.androidx.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.integration.compiler)
    //javapoet
    implementation(libs.squareup.javapoet)
    //data store
    implementation(libs.androidx.datastore)
    //crypto tink
    implementation(libs.cryptoTink)
    //bouncyCastle
    enforcedPlatform(libs.bouncyCastle.bcprov)
    implementation(libs.bouncyCastle.bcpkix)
    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    //retrofit
    api(libs.squareup.retrofit)
    implementation(libs.squareup.gson)
    implementation(libs.logging.interceptor)
    //kotlinx serialization json
    implementation(libs.kotlinx.serialization.json)
}


kapt {
    correctErrorTypes = true
}
