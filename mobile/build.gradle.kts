plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.oscar.hydrosense"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.oscar.hydrosense"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.5.1"
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
    packagingOptions {
        resources {
            excludes += listOf("META-INF/INDEX.LIST", "META-INF/io.netty.versions.properties")
        }
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    //librerias de compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.8.0")

    //librerias de testing de compose
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //librerias de android como wearable, material, etc
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.play.services.wearable)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    wearApp(project(":wear"))

    //libreria para iconos de material
    implementation("androidx.compose.material:material-icons-core:1.6.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    //libreria pra fuentes de google
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")

    //retofit, dagger y hilt
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.2")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //libreria de navegacion
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //libreria para guardar en datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //libreria de hiveqm(sockets)
    implementation ("com.hivemq:hivemq-mqtt-client:1.3.3")

    //libreria para solicitar permisos
    implementation ("com.google.accompanist:accompanist-permissions:0.32.0")

}