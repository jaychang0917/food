plugins {
    id("com.android.library")
}

dependencies {
    testImplementation(Libraries.JUNIT4)
    testImplementation(Libraries.JUNIT5)
    testImplementation(Libraries.MOCKK)
    testImplementation(Libraries.TRUTH_ASSERT)
    testImplementation(Libraries.ANDROID_TEST_RUNNER)
    testImplementation(Libraries.ANDROID_TEST_CORE) // Delegate to Robolectric under the hood.
    testImplementation(Libraries.ROBOLECTRIC)

    androidTestImplementation(Libraries.ANDROID_TEST_CORE)
    androidTestImplementation(Libraries.ANDROID_TEST_RULE)
    androidTestImplementation(Libraries.ANDROID_TEST_RUNNER)
    androidTestImplementation(Libraries.TRUTH_ASSERT)
    androidTestImplementation(Libraries.MOCKK_ANDROID)
    androidTestImplementation(Libraries.ESPRESSO_CORE)
    androidTestImplementation(Libraries.ESPRESSO_CORE_CONTRIB)
}
