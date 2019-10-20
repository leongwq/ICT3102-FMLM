package com.example.fmlm

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.fmlm.fragment.login.LoginComponentFragment
import com.example.fmlm.fragment.profile.ProfileComponentFragment
import com.example.fmlm.fragment.routing.RoutingComponentFragment
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EnergyTest {

    @Rule
    @JvmField
    var rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        val transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
    }

    @Test
    fun replicateAverage() {
        for(i in 1..3){
            var transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
            var fragment: Fragment = RoutingComponentFragment()
            transaction.replace(R.id.fragment_frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            var cities: Array<String> = arrayOf("Phoenix", "Portland", "Seattle")
            for (i in 1..3){
                Espresso.onView(ViewMatchers.withId(R.id.TextInputEditText))
                    .perform(ViewActions.replaceText(cities[(i - 1) % 5])).perform(ViewActions.closeSoftKeyboard())
                Espresso.onView(ViewMatchers.withId(R.id.button_confirm)).perform(ViewActions.click())
            }
            transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
            fragment = LoginComponentFragment()
            transaction.replace(R.id.fragment_frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            Thread.sleep(1000)
            transaction = rule.getActivity().getSupportFragmentManager().beginTransaction()
            fragment = ProfileComponentFragment()
            transaction.replace(R.id.fragment_frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commitAllowingStateLoss()
            Thread.sleep(1000)
            //textviews
            Espresso.onView(ViewMatchers.withId(R.id.edittext_name))
                .perform(ViewActions.replaceText("Orph")).perform(ViewActions.closeSoftKeyboard())
            //close keyboard here because edittext_reason is hidden by keyboard
            Espresso.onView(ViewMatchers.withId(R.id.edittext_age)).perform(ViewActions.typeText("24")).perform(
                ViewActions.closeSoftKeyboard()
            )
            //spinner
            Espresso.onView(ViewMatchers.withId(R.id.spinner_gender)).perform(ViewActions.click())
            Espresso.onData(
                CoreMatchers.allOf(
                    CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                    CoreMatchers.`is`("Male")
                )
            ).perform(ViewActions.click())
            //onView(withId(R.id.spinner_gender)).check(matches(withText(containsString("Male"))))
            //spinner 2
            Espresso.onView(ViewMatchers.withId(R.id.spinner_method)).perform(ViewActions.click())
            Espresso.onData(
                CoreMatchers.allOf(
                    CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                    CoreMatchers.`is`("Taxi")
                )
            ).perform(ViewActions.click())
            //onView(withId(R.id.spinner_method)).check(matches(withText(containsString("Taxi"))))
            //close keyboard before any out of activity calls or you'll get some error about event injection - might need to sleep on slow devices
            Espresso.onView(ViewMatchers.withId(R.id.edittext_reason))
                .perform(ViewActions.typeText("haha")).perform(ViewActions.closeSoftKeyboard())
            //submit
            Espresso.onView(ViewMatchers.withId(R.id.button_submit)).perform(ViewActions.click())
        }
    }

}