plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.android.ksp)
}

android {
    namespace = "com.softwarecleandevelopment.cryptowallet"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.softwarecleandevelopment.cryptowallet"
        minSdk = 24
        targetSdk = 36
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
        compose = true
    }
}
hilt {
    enableAggregatingTask = false
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //coroutine
    implementation(libs.androidx.coroutine.android)
    implementation(libs.androidx.coroutine.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //dagger-hilt
    implementation(libs.androidx.hilt.android)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.integration.compiler)
    //kethereum
    implementation(libs.androidx.kethereum.bip39)
    implementation(libs.androidx.kethereum.bip32)
    implementation(libs.androidx.kethereum.wallet)
    implementation(libs.androidx.kethereum.model)
    implementation(libs.androidx.kethereum.bip39Wordlist)
    implementation(libs.androidx.kethereum.cryptoImplBouncycastle)
    //javapoet
    implementation(libs.squareup.javapoet)
}
