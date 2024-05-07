import org.jetbrains.kotlin.fir.declarations.builder.buildScript

buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
    }

    dependencies {
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}