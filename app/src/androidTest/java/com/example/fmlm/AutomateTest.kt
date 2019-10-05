package com.example.fmlm

import android.os.Environment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.*


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
        /**
         * Very device and launcher dependent
         * Dump your screen hierarchy using the getObjects helper function below and check the xml for the right node
         * Below works for emulator with stock launcher on Android 10
         */
        for(i in 1..3){
            val recent : UiObject2   = mDevice.wait(Until.findObject(By.res("com.android.systemui:id/recent_apps")),5000)
            recent.click()
            val recentApps = mDevice.findObject(UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/drag_layer"))
            recentApps.clickAndWaitForNewWindow()
            onView(ViewMatchers.withId(R.id.edittext_name)).perform(ViewActions.typeText(i.toString())).perform(
                ViewActions.closeSoftKeyboard())
        }
        for(i in 1..3){
            mDevice.pressHome()
            val appViews = mDevice.findObject(UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/workspace"))
            appViews.swipeUp(10)
            val app : UiObject2   = mDevice.wait(Until.findObject(By.text("FMLM")),5000)
            app.click()
            onView(ViewMatchers.withId(R.id.edittext_name)).perform(ViewActions.typeText(i.toString())).perform(
                ViewActions.closeSoftKeyboard())
        }
        /**
         * Below works for Nova Launcher on the Pixel 2 XL
         */
        /*
        for(i in 1..3) {
            mDevice.pressHome()
            val allAppsButton: UiObject = mDevice.findObject(UiSelector().text("Apps"))
            allAppsButton.clickAndWaitForNewWindow()
            val appViews = mDevice.findObject(UiSelector().resourceId("com.teslacoilsw.launcher:id/drag_layer"))
            val ourApp = mDevice.findObject(UiSelector().text("FMLM"))
            while (!ourApp.exists()){
                appViews.swipeLeft(5)
            }
            ourApp.clickAndWaitForNewWindow()
        }*/
    }

    /**
     * Debugging code dumps the hierarchy of the screen contents into the Download folder. Call on this whenever you need it
     * Use device file explorer on bottom left to copy the file from the emulator/phone to your PC
     */
    //@Test
    fun dump_screen() {
        val mDevice = UiDevice.getInstance(getInstrumentation())
        mDevice.pressHome()
        val dumpXml = "dump.xml"
        val dump = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dumpXml)
        if (dump.exists()) {
            dump.delete()
        }
        mDevice.dumpWindowHierarchy(dump)
    }

}
