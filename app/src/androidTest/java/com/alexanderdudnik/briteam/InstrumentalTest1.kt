package com.alexanderdudnik.briteam

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.alexanderdudnik.briteam.views.BarcodeListAdapter
import junit.framework.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.lang.Thread.sleep


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class InstrumentalTest1 {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun check_recycle_list() {
        activityRule.activity.run {
            val rv = findViewById<RecyclerView>(R.id.rv_list)
            assertEquals(
                "RecycleView count",
                0,
                rv.adapter!!.itemCount
            )

        }

        sleep(21000)

        activityRule.activity.run {
            val rv = findViewById<RecyclerView>(R.id.rv_list)
            assertEquals(
                "RecycleView count",
                4,
                rv.adapter!!.itemCount
            )

        }

        onView(withId(R.id.rv_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BarcodeListAdapter.ViewHolder>(2, click()))

        sleep(1000)

        activityRule.activity.run {
            val rv = findViewById<RecyclerView>(R.id.rv_list)
            val adapter = rv.adapter!! as BarcodeListAdapter
            val item = adapter.getItem(2)

            var toolbar = supportActionBar!!

            assertEquals(
                "Toolbar title",
                item.data.barcode,
                toolbar.title
            )

            assertEquals(
                "Toolbar subtitle",
                item.data.type,
                toolbar.subtitle
            )

            val toolbar2 = findViewById<Toolbar>(R.id.toolbar)

            assertEquals(
                "Toolbar color",
                item.data.getColorValue(),
                (toolbar2.background as ColorDrawable).color
            )
        }


    }

}


