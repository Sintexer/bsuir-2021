plugins {
    kotlin("jvm") version "1.5.21"
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "com.ilyabuhlakou.mod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.nield:kotlin-statistics:1.2.1")
    implementation("no.tornado:tornadofx:1.7.20")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

javafx {
    version = "12"
    modules("javafx.controls", "javafx.fxml")
}
