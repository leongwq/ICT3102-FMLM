package com.example.fmlm

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.fmlm.fragment.routing.RoutingComponentFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoutingTest {

    @Rule
    @JvmField
    var rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        val transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
        var fragment: Fragment = RoutingComponentFragment()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //transaction.commitAllowingStateLoss()
    }

    @Test
    fun getFiveCities() {
        var cities: Array<String> = arrayOf("Phoenix", "Portland", "Seattle", "Houston", "Miami")
        //can be used for stress testing maybe???
        for (i in 1..10){
            onView(withId(R.id.TextInputEditText)).perform(replaceText(cities[(i-1)%5])).perform(closeSoftKeyboard())
            onView(withId(R.id.button_confirm)).perform(click())
        }
        //onView(withId(R.id.map)).perform(swipeUp())
        //Thread.sleep(5000)
    }
}