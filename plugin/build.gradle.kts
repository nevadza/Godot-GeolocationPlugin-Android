plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}


val pluginName = "Geolocation"
val pluginPackageName = "de.wolfbeargames.geolocationplugin"

base {
    archivesName = pluginName
}

android {
    namespace = pluginPackageName
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        manifestPlaceholders["godotPluginName"] = pluginName
        manifestPlaceholders["godotPluginPackageName"] = pluginPackageName
        buildConfigField("String", "GODOT_PLUGIN_NAME", "\"${pluginName}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.godotengine.godot)
    implementation(libs.google.gms)
}

/*val copyDescriptorsToOutput by tasks.registering(Copy::class) {
    description = "Copies the Godot plugin descriptors to output directory."

    from("godot_descriptors")
    into("${layout.buildDirectory.get()}/outputs/aar")
}

tasks.named("build").configure {
    finalizedBy(copyDescriptorsToOutput)
}*/