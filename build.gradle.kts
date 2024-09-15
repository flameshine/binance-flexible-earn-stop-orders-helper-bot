val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val telegramBotApiVersion: String by project
val binanceConnectorVersion: String by project
val lombokVersion: String by project
val jaxbApiVersion: String by project

plugins {
    java
    id("io.quarkus")
}

group = "com.flameshine"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-mysql")
    implementation("org.telegram:telegrambots:$telegramBotApiVersion")
    implementation("io.github.binance:binance-connector-java:$binanceConnectorVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    runtimeOnly("javax.xml.bind:jaxb-api:$jaxbApiVersion") // required by Quarkus
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}