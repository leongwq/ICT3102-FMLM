package com.example.fmlm

import android.content.pm.ActivityInfo
import android.os.Environment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
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
import java.io.File
import androidx.test.espresso.ViewAction
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.app.Activity
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.runner.lifecycle.Stage
import org.hamcrest.Matcher


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
        //val a : OrientationChangeAction = OrientationChangeAction()
        //onView(isRoot()).perform(a.orientationLandscape())
        //Thread.sleep(100)
        //rule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //for stock launcher but not working
        //val allAppsButton: UiObject = mDevice.findObject(UiSelector().description("Apps list"))
        //allAppsButton.swipeUp(100)
        /*
        mDevice.pressHome()
        val recentApp = mDevice.findObject(UiSelector().resourceId("com.android.systemui:id/recent_apps")) //doesn't work
        recentApp.clickAndWaitForNewWindow()
        dump_screen()*/
        /**
         * Very device and launcher dependent
         * Dump your screen hierarchy using the getObjects helper function below and check the xml for the right node
         * Below works for Nova Launcher on the Pixel 2 XL
         */
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
        }
    }

    inner class OrientationChangeAction private constructor(private val orientation: Int) :ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "change orientation to $orientation"
        }

        override fun perform(uiController: UiController, view: View) {
            uiController.loopMainThreadUntilIdle()
            val activity = view.getContext() as Activity
            activity.requestedOrientation = orientation

            val resumedActivities =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.isEmpty()) {
                throw RuntimeException("Could not change orientation")
            }
        }

        fun orientationLandscape(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        fun orientationPortrait(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
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
