buildscript {
    repositories {
        kotlinEap()
        google()
        jcenter()
        gradle()
    }
    dependencies {
        classpath(Dep.androidGradlePlugin)
        classpath(Dep.daggerHiltGradlePlugin)
        classpath(Dep.detektGradlePlugin)
        classpath(Dep.firebaseCrashlyticsGradlePlugin)
        classpath(Dep.googleServicesPlugin)
        classpath(Dep.kotlinPlugin)
        classpath(Dep.ktlintGradlePlugin)
    }
}

allprojects {
    repositories {
        kotlinEap()
        google()
        jcenter()
    }

    configurations.all {
        // https://github.com/noties/Markwon/issues/148
        exclude(group = "org.jetbrains", module = "annotations-java5")
        exclude(group = "com.google.guava", module = "listenablefuture")

        resolutionStrategy {
            force(Dep.androidxAppCompat) // Pin version for night mode bug
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
