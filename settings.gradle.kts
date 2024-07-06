pluginManagement {
    repositories {
        maven {
            url = uri("file:///D:/maven")
        }
        google()
        maven {
            url = uri("file:///D:/maven")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("file:///D:/maven")
        }
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }

    }

}

rootProject.name = "NewReceiptCreator"
include(":app")

 