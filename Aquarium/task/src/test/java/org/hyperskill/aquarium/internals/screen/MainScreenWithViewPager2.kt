package org.hyperskill.aquarium.internals.screen

import android.app.Activity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.hyperskill.aquarium.internals.AquariumTest
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListNames
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import java.util.concurrent.TimeUnit

class MainScreenWithViewPager2<T : Activity> (
    private val test: AquariumTest<T>, val
    numPages: Int = fakeListNames.size,
    initViews: Boolean = true
) {

    companion object {
        const val ID_VIEWPAGER2 = "viewpager2"
        const val ID_TAB_LAYOUT = "tab_layout"
    }

    var pageIndex = 0
        private set

    val viewPager2: ViewPager2 by lazy {
        with(test){
            activity.findViewByString<ViewPager2>(ID_VIEWPAGER2).also { viewPager2 ->
                val messageAdapterNull = "Viewpager2 should have an adapter"
                assertNotNull(messageAdapterNull, viewPager2.adapter)
            }
        }
    }

    val tabLayout: TabLayout by lazy {
        with(test) {
            activity.findViewByString(ID_TAB_LAYOUT)
        }
    }

    fun selectPageThroughTab(pageIndex: Int) = with(test) {
        this@MainScreenWithViewPager2.pageIndex = pageIndex
        tabLayout.clickTabAndRun(pageIndex)
    }

    fun nextPage() = with(test) {
        pageIndex = (pageIndex + 1).mod(numPages)
        viewPager2.setCurrentItem(pageIndex, false)
        shadowLooper.idleFor(500, TimeUnit.MILLISECONDS)
    }

    fun previousPage() = with(test) {
        pageIndex = (pageIndex - 1).mod(numPages)
        viewPager2.setCurrentItem(pageIndex, false)
        shadowLooper.idleFor(500, TimeUnit.MILLISECONDS)
    }

    fun assertTabTextContent(pageIndex: Int, names: List<String> = fakeListNames) = with(test) {
        val tab = tabLayout.getTabAt(pageIndex)
        val expectedText = names[pageIndex]
        val actualText = tab?.text
        assertEquals("On tab with index $pageIndex", expectedText, actualText)
    }

    init {
        if(initViews) {
            viewPager2
            tabLayout
        }
    }
}