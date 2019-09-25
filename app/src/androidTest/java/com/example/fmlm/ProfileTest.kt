package com.example.fmlm

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ProfileTest {

    //Original test
    /*
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        
        assertEquals("com.example.fmlm", appContext.packageName)
    }*/


    //launch Main activity
    @Rule @JvmField
    var rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        val transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
    }

    @Test
    fun profileTest() {
        //textviews
        onView(withId(R.id.edittext_name)).perform(typeText("Orph"))
        //close keyboard here because edittext_reason is hidden by keyboard
        onView(withId(R.id.edittext_age)).perform(typeText("24")).perform(closeSoftKeyboard())
        //spinner
        onView(withId(R.id.spinner_gender)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        //onView(withId(R.id.spinner_gender)).check(matches(withText(containsString("Male"))))
        //spinner 2
        onView(withId(R.id.spinner_method)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Taxi"))).perform(click())
        //onView(withId(R.id.spinner_method)).check(matches(withText(containsString("Taxi"))))
        //close keyboard before any out of activity calls or you'll get some error about event injection - might need to sleep on slow devices
        onView(withId(R.id.edittext_reason)).perform(typeText("haha")).perform(closeSoftKeyboard())
        //submit
        for(i in 1..3) {
            onView(withId(R.id.button_submit)).perform(click())
        }
        //add sleep or some won't be sent till next run
        Thread.sleep(5000)
    }

    /**
     * Gets all the views available to the test. Good for debugging purposes
     */
    //@Test
    fun getAllViewsInTest() {
        //pretty long chain
        System.out.println(getViewTree(rule.getActivity().getWindow().getDecorView().getRootView() as ViewGroup))
    }

    fun getViewTree(root: ViewGroup): String{
        fun getViewDesc(v: View): String{
            val res = v.getResources()
            val id = v.getId()
            return "[${v::class.simpleName}]: " + when(true){
                res == null -> "no_resouces"
                id > 0 -> try{
                    res.getResourceName(id)
                } catch(e: android.content.res.Resources.NotFoundException){
                    "name_not_found"
                }
                else -> "no_id"
            }
        }

        val output = StringBuilder(getViewDesc(root))
        for(i in 0 until root.getChildCount()){
            val v = root.getChildAt(i)
            output.append("\n").append(
                if(v is ViewGroup){
                    getViewTree(v).prependIndent("  ")
                } else{
                    "  " + getViewDesc(v)
                }
            )
        }
        return output.toString()
    }
}
