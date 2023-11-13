plugins {
    id("com.android.application")
}

android {
    namespace = "iot.app.smarthome"
    compileSdk = 33


    defaultConfig {
        applicationId = "iot.app.smarthome"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    //开启数据绑定
    dataBinding {
        enable = true
    }
//    buildFeatures {
//        dataBinding = true
//    }

//    kts自定义构建apk的名字
//    方法①  虽然能用但是提示JAVA已经弃用了。
//    applicationVariants.all {
//        outputs.withType<com.android.build.gradle.api.ApkVariantOutput> {
//            outputFileName = "SmartHome_${buildType.name}_v${versionName}.apk"
//        }
//    }
//    方法②
    android.applicationVariants.all {
        // 编译类型
        val buildType = this.buildType.name
        outputs.all {
            // 判断是否是输出 apk 类型
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "SmartHome_${buildType}_${defaultConfig.versionName}.apk"
            }
        }

    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    implementation("androidx.navigation:navigation-ui:2.3.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    //引入Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("org.litepal.guolindev:core:3.2.3")

//    导入MPAndroidChart 绘制图表
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

//    //修改kotlin-stdlib-jdk加载方式
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    //实训课的包
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")
    implementation ("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.1")
    implementation ("org.apache.commons:commons-lang3:3.8.1")

}