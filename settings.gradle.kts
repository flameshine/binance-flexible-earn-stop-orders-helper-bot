pluginManagement {

    val quarkusPluginId: String by settings
    val quarkusPluginVersion: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}

rootProject.name = "binance-flexible-earn-stop-orders-helper-bot"