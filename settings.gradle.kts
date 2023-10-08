pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        //阿里云镜像
        maven {
            url=uri("https://maven.aliyun.com/repository/public/")
        }
    }
}

rootProject.name = "smartHome"
include(":app")
 