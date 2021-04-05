package ant.vit.paesidelmondo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ant.vit.paesidelmondo.ui.CountriesActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ant.vit.paesidelmondo", appContext.packageName)
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(CountriesActivity::class.java)

    @Test
    fun startCountriesActivity() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withText(appContext.getString(R.string.app_name))).check(matches(isDisplayed()))
    }

    @Test
    fun clickLanguageFab() {
        onView(withId(R.id.languageFab))
            .perform(click())
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickCountry() {
        onView(withId(R.id.recyclerView))
            .perform(click())
            .check(matches(isDisplayed()))
    }

}