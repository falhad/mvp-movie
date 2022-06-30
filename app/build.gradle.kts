plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")


//    id 'com.android.application' version '7.2.1' apply false
//    id 'com.android.library' version '7.2.1' apply false
//    id 'org.jetbrains.kotlin.android' version '1.7.0' apply false

    kotlin("kapt")
}


android {
    compileSdk = 32

    defaultConfig {
        applicationId  = "dev.falhad.movieshowcase"
        minSdk = 24
        targetSdk = 32
        versionCode = 2
        versionName = "1.1.$versionCode"
        setProperty("archivesBaseName", "$applicationId-$versionName(c$versionCode)")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            versionNameSuffix = "release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }


    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-rc01")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-rc01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")



    implementation("androidx.annotation:annotation:1.4.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    implementation("com.google.android.material:material:1.6.1")


    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0-rc02")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0-rc02")

    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-android-compat:3.2.0")

    implementation("io.ktor:ktor-client-core:2.0.2")
    implementation("io.ktor:ktor-client-serialization:2.0.2")
    implementation("io.ktor:ktor-client-android:2.0.2")
    implementation("io.ktor:ktor-client-cio:2.0.2")

    implementation ("com.github.mike14u:shimmer-recyclerview-x:1.0.4")


    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")


    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.9")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")

    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation( "com.squareup.moshi:moshi-adapters:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.9.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("com.afollestad.rxkprefs:core:2.0.3")
    implementation("com.afollestad.rxkprefs:rxjava:2.0.3")
    implementation("com.afollestad.rxkprefs:coroutines:2.0.3")

    implementation("com.airbnb.android:lottie:5.2.0")

    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.afollestad.material-dialogs:bottomsheets:3.3.0")
    implementation("com.afollestad.material-dialogs:lifecycle:3.3.0")


    implementation("com.mikepenz:materialdrawer:8.4.5")
    implementation("com.mikepenz:materialdrawer-nav:8.4.5")
    implementation("com.mikepenz:materialdrawer-iconics:8.4.5")
    implementation("com.mikepenz:google-material-typeface:4.0.0.2-kotlin@aar")


    implementation("io.coil-kt:coil:2.1.0")
    implementation("io.coil-kt:coil-svg:2.1.0")


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}