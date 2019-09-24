package com.example.fmlm

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.rule.ActivityTestRule
import com.example.fmlm.fragment.profile.ProfileComponentFragment
import org.junit.Rule
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    //Original test
    /*
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        
        assertEquals("com.example.fmlm", appContext.packageName)
    }*/


    @Rule @JvmField
    var rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        val transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
        var fragment: Fragment = ProfileComponentFragment()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @Test
    fun textViewTest() {
        /*
        onView(withId(R.id.edittext_username)).perform(typeText("Steve")) -- still can't be found in view of test...
        onView(withId(R.id.button_register)).perform(click())
        //onView(withText("Hello Steve!")).check(matches(isDisplayed()))*/
    }

    /**
     * Gets all the views available to the test. Good for debugging purposes
     */
    @Test
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
