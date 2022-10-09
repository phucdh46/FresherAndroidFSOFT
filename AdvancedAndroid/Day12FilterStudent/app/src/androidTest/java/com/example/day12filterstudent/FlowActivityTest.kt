package com.example.day12filterstudent


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

//@RunWith(AndroidJUnit4::class)
class FlowActivityTest {

    @get: Rule
    val mScenario = ActivityScenarioRule(FlowActivity::class.java)

    //text search change
    @Test
    fun testSearchChange() {
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(
                ViewActions.typeText("name 1")
            )
        Espresso.onView(ViewMatchers.withId(R.id.rv))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("name 1"))
                )
            )
    }

    //test search submit
    @Test
    fun testSearchSubmit() {
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(
                ViewActions.typeText("name 1")
            )
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.rv))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("name 1"))
                )
            )
    }

    //test special char in string search
    @Test
    fun testSpecialChar() {
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(ViewActions.typeText("@"))
        Espresso.onView(ViewMatchers.withId(R.id.tv))
            .check(ViewAssertions.matches(ViewMatchers.withText("special characters")))
    }
}