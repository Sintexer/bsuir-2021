plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.ilyabuhlakou.mod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.nield:kotlin-statistics:1.2.1")
}
