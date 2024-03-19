plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.mikepenz.aboutlibraries.plugin") version "10.10.0"
}

android {
    namespace = "cc.sovellus.vrcaa"
    compileSdk = 34

    defaultConfig {
        applicationId = "cc.sovellus.vrcaa"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "GIT_HASH", "\"${getGitHash()}\"")
        buildConfigField("String", "GIT_BRANCH", "\"${getBranch()}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/androidx.compose.material3_material3.version"
        }
    }

    flavorDimensions += "type"
    productFlavors {
        create("standard") {
            isDefault = true
            dimension = "type"
        }
        create("quest") {
            dimension = "type"
        }
    }
}

// credit: https://github.com/amwatson/CitraVR/blob/master/src/android/app/build.gradle.kts#L255C1-L275C2
fun getGitHash(): String =
    runGitCommand(ProcessBuilder("git", "rev-parse", "--short", "HEAD")) ?: "invalid-hash"

fun getBranch(): String =
    runGitCommand(ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD")) ?: "invalid-branch"

fun runGitCommand(command: ProcessBuilder) : String? {
    try {
        command.directory(project.rootDir)
        val process = command.start()
        val inputStream = process.inputStream
        val errorStream = process.errorStream
        process.waitFor()

        return if (process.exitValue() == 0) {
            inputStream.bufferedReader()
                .use { it.readText().trim() } // return the value of gitHash
        } else {
            val errorMessage = errorStream.bufferedReader().use { it.readText().trim() }
            logger.error("Error running git command: $errorMessage")
            return null
        }
    } catch (e: Exception) {
        logger.error("$e: Cannot find git")
        return null
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0")
    implementation("com.sealwu.jsontokotlin:library:3.7.4")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-alpha02")
    implementation("cafe.adriel.voyager:voyager-screenmodel:1.1.0-alpha02")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.1.0-alpha02")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.1.0-alpha02")
    implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-alpha02")
    implementation("cafe.adriel.voyager:voyager-livedata:1.1.0-alpha02")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.33.2-alpha")
    implementation("androidx.compose.material:material:1.6.3")
    implementation("androidx.compose.material:material-icons-extended:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("com.mikepenz:aboutlibraries-core:10.10.0")
    implementation("com.mikepenz:aboutlibraries-compose-m3-android:10.10.0@aar")
    implementation ("androidx.glance:glance-appwidget:1.0.0")
    implementation ("androidx.glance:glance-material3:1.0.0@aar")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}