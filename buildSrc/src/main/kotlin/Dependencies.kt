object Versions {
    const val androidGradlePlugin = "4.2.0-alpha03"
    const val androidxActivity = "1.2.0-alpha06"
    const val androidxAppCompat = "1.2.0-alpha02"
    const val androidxBrowser = "1.3.0-alpha04"
    const val androidxCore = "1.4.0-alpha01"
    const val androidxFragment = "1.3.0-alpha06"
    const val androidxLifecycle = "2.3.0-alpha05"
    const val androidxPreference = "1.1.1"
    const val androidxRecyclerView = "1.2.0-alpha04"
    const val androidxRoom = "2.3.0-alpha01"
    const val androidxSecurity = "1.1.0-alpha01"
    const val androidxSwipeRefreshLayout = "1.1.0-rc01"
    const val androidxTestCore = "1.3.0-rc01"
    const val androidxViewPager2 = "1.1.0-alpha01"
    const val androidxWork = "2.4.0-rc01"
    const val apacheCommonsText = "1.8"
    const val betterLinkMovementMethod = "2.2.0"
    const val chucker = "3.2.0"
    const val coil = "0.11.0"
    const val compose = "0.1.0-dev13"
    const val composeKotlinCompiler = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
    const val constraintLayout = "2.0.0-beta7"
    const val dagger = "2.28.1"
    const val detekt = "1.10.0"
    const val espresso = "3.1.0"
    const val firebaseAnalytics = "17.4.3"
    const val firebaseCrashlytics = "17.1.0"
    const val firebaseCrashlyticsGradlePlugin = "2.2.0"
    const val googleServices = "4.3.3"
    const val insetter = "0.3.0"
    const val jUnit = "4.12"
    const val klock = "1.11.12"
    const val kotlin = "1.3.72"
    const val kotlinxCoroutines = "1.3.7"
    const val ktlintGradle = "9.2.1"
    const val markwon = "4.4.0"
    const val materialComponents = "1.3.0-alpha01"
    const val mockito = "3.0.0"
    const val mockitoKotlin = "2.2.0"
    const val moshi = "1.9.3"
    const val okHttp = "4.7.2"
    const val playCore = "1.7.3"
    const val prism4j = "2.0.0"
    const val processPhoenix = "2.0.0"
    const val retrofit = "2.9.0"
    const val robolectric = "4.3.1"
    const val testRunner = "1.1.0"
    const val timber = "4.7.1"
}

object Dep {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val androidxActivity = "androidx.activity:activity-ktx:${Versions.androidxActivity}"
    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val androidxBrowser = "androidx.browser:browser:${Versions.androidxBrowser}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val androidxFragment = "androidx.fragment:fragment-ktx:${Versions.androidxFragment}"
    const val androidxLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}"
    const val androidxPreference = "androidx.preference:preference:${Versions.androidxPreference}"
    const val androidxRecyclerView = "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}"
    const val androidxRoomKtx = "androidx.room:room-ktx:${Versions.androidxRoom}"
    const val androidxRoomProcessor = "androidx.room:room-compiler:${Versions.androidxRoom}"
    const val androidxRoomRuntime = "androidx.room:room-runtime:${Versions.androidxRoom}"
    const val androidxSecurity = "androidx.security:security-crypto:${Versions.androidxSecurity}"
    const val androidxSwipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidxSwipeRefreshLayout}"
    const val androidxTestCore = "androidx.test:core:${Versions.androidxTestCore}"
    const val androidxViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    const val androidxViewPager2 = "androidx.viewpager2:viewpager2:${Versions.androidxViewPager2}"
    const val androidxWork = "androidx.work:work-runtime-ktx:${Versions.androidxWork}"
    const val apacheCommonsText = "org.apache.commons:commons-text:${Versions.apacheCommonsText}"
    const val betterLinkMovementMethod = "me.saket:better-link-movement-method:${Versions.betterLinkMovementMethod}"
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val composeCore = "androidx.ui:ui-core:${Versions.compose}"
    const val composeFoundation = "androidx.ui:ui-foundation:${Versions.compose}"
    const val composeLayout = "androidx.ui:ui-layout:${Versions.compose}"
    const val composeMaterial = "androidx.ui:ui-material:${Versions.compose}"
    const val composeTooling = "androidx.ui:ui-tooling:${Versions.compose}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}"
    const val firebaseCrashlyticsGradlePlugin = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsGradlePlugin}"
    const val googleServicesPlugin = "com.google.gms:google-services:${Versions.googleServices}"
    const val insetter = "dev.chrisbanes:insetter-ktx:${Versions.insetter}"
    const val klock = "com.soywiz.korlibs.klock:klock:${Versions.klock}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    const val markwonCore = "io.noties.markwon:core:${Versions.markwon}"
    const val markwonHtml = "io.noties.markwon:html:${Versions.markwon}"
    const val markwonImageCoil = "io.noties.markwon:image-coil:${Versions.markwon}"
    const val markwonLinkify = "io.noties.markwon:linkify:${Versions.markwon}"
    const val markwonStrikethrough = "io.noties.markwon:ext-strikethrough:${Versions.markwon}"
    const val markwonSyntaxHighlight = "io.noties.markwon:syntax-highlight:${Versions.markwon}"
    const val markwonTables = "io.noties.markwon:ext-tables:${Versions.markwon}"
    const val markwonTaskList = "io.noties.markwon:ext-tasklist:${Versions.markwon}"
    const val materialComponents = "com.google.android.material:material:${Versions.materialComponents}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val okHttpMock = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    const val playCore = "com.google.android.play:core:${Versions.playCore}"
    const val prism4jBundler = "io.noties:prism4j-bundler:${Versions.prism4j}"
    const val processPhoenix = "com.jakewharton:process-phoenix:${Versions.processPhoenix}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
}
