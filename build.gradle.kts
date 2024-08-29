plugins {
    java
}

group = "com.flameshine"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.telegram:telegrambots:6.9.7.1")
}