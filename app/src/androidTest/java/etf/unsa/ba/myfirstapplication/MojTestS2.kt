package etf.unsa.ba.myfirstapplication




import android.widget.EditText
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasLinks
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import etf.unsa.ba.myfirstapplication.enumClass.ProfilOkusaBiljke
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.EasyMock2Matchers.equalTo
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MojTestS2 {


    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )



    @Test
    fun validacija1(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        /*onView(withId(R.id.nazivET)).perform(typeText("testNaziv"))
        onView(withId(R.id.porodicaET)).perform(typeText("testPorodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("testUpozorenje"))
        onView(withId(R.id.jeloET)).perform(typeText("testJelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(typeText("testJelo2"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())*/

        /*val pOView = activityScenarioRule.scenario.onActivity {
            val pOView = it.findViewById<ListView>(R.id.profilOkusaLV)
            val itemCount = pOView.adapter!!.count
            MatcherAssert.assertThat(itemCount, OrderingComparison.greaterThan(4))
        }*/
/*
        onData(
            allOf(
                Is(instanceOf(ProfilOkusaBiljke::class.java)),
                CoreMatchers.containsString("LJU")
            )
        ).perform(click())*/

        onView(withId(R.id.profilOkusaLV))
            .check(matches(withText(containsString("MEN"))))

        /* onView(withId(R.id.klimatskiTipLV)).perform(click())

        onView(withId(R.id.zemljisniTipLV)).perform(click())

        onView(withId(R.id.medicinskaKoristLV)).perform(click())*/
    }


    @Test
    fun validacija2(){
        try {
            onView(withId(R.id.novaBiljkaBtn)).perform(click())
            onView(withId(R.id.nazivET)).perform(typeText("t"))
            onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        }catch (e: Exception){
            MatcherAssert.assertThat(e.message, CoreMatchers.containsString("Error performing"))
        }

    }

    @Test
    fun openCamera(){
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())



    }


}