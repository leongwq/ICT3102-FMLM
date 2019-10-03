package com.example.fmlm

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        val device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()
    }

}
