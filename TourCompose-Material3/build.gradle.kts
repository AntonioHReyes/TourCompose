plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.tonyakitori.apps.tourcompose.material3"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        aarMetadata {
            minCompileSdk = 34
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {
    // Dependencia al módulo base
    implementation(project(":TourCompose"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.foundation)

    // Material3 específico para este módulo
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

afterEvaluate {
    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.github.tonyakitori"
                artifactId = "tourcompose-material3"
                version = "2.0.0"

                from(components["release"])

                pom {
                    name.set("TourCompose Material3")
                    description.set("Material3 wrapper for TourCompose library")
                    url.set("https://github.com/tonyakitori/TourCompose")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("tonyakitori")
                            name.set("Antonio Huerta")
                            email.set("develop@tonyakitori.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/tonyakitori/TourCompose.git")
                        developerConnection.set("scm:git:ssh://github.com/tonyakitori/TourCompose.git")
                        url.set("https://github.com/tonyakitori/TourCompose")
                    }
                }
            }
        }
    }
}