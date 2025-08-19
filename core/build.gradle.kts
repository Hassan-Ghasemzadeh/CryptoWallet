plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.android.ksp)
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
    //kethereum
    implementation(libs.androidx.kethereum.bip39)
    implementation(libs.androidx.kethereum.bip32)
    implementation(libs.androidx.kethereum.crypto)
    implementation(libs.androidx.kethereum.wallet)
    implementation(libs.androidx.kethereum.model)
    implementation(libs.androidx.kethereum.bip39Wordlist)
    implementation(libs.androidx.kethereum.cryptoImplBouncycastle)
    //dagger-hilt
    implementation(libs.androidx.hilt.android)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.integration.compiler)
    //javapoet
    implementation(libs.squareup.javapoet)
    //data store
    implementation(libs.androidx.datastore)
    //crypto tink
    implementation(libs.cryptoTink)
}