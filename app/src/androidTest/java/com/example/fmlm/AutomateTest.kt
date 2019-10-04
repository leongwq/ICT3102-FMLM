package com.example.fmlm

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.UiObject


@RunWith(AndroidJUnit4::class)
class AutomateTest {

    @Rule
    @JvmField
    var rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        val transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
    }

    @Test
    fun pressHome() {
        val mDevice = UiDevice.getInstance(getInstrumentation())
        mDevice.pressHome()
        /*
        val allAppsButton: UiObject = mDevice.findObject(UiSelector().description("Apps")) //crashes on android 10
        allAppsButton.clickAndWaitForNewWindow()*/
    }

    /**
     * Debugging code to get all uidevice objects
     */
    //@Test
    fun getObjects() {
        val selector = UiSelector().clickable(true)
    }

}
