package org.hyperskill.aquarium

import org.hyperskill.aquarium.internals.AquariumTest
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListDescriptions
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListImageUrls
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListNames
import org.hyperskill.aquarium.internals.screen.MainScreenWithViewPager2
import org.hyperskill.aquarium.internals.screen.PageScreen
import org.hyperskill.aquarium.internals.shadow.CustomShadowAsyncDifferConfig
import org.hyperskill.aquarium.internals.shadow.CustomShadowPicasso
import org.hyperskill.aquarium.internals.shadow.CustomShadowRequestCreator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Config(shadows = [
    CustomShadowPicasso::class,
    CustomShadowRequestCreator::class,
    CustomShadowAsyncDifferConfig::class
])
class Stage4UnitTest : AquariumTest<MainActivity>(MainActivity::class.java) {

    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }


    @Test
    fun test00_checkTheTabLayout() {
        testActivity {
            MainScreenWithViewPager2(this, initViews = false).apply {
                tabLayout
            }
        }
    }

    @Test
    fun test01_checkingTabCount() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                val messageError = "tabLayout tab count does not match input lists size"
                val tabCount = tabLayout.tabCount
                assertEquals(messageError, 3, tabCount)
            }
        }
    }

    @Test
    fun test02_checkingIfViewPagerPagePositionMatchesTabLayoutTabPosition() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                val messageError = "ViewPager2 position is not matching tabLayout position"

                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)

                nextPage()
                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)

                nextPage()
                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)

                previousPage()
                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)

                selectPageThroughTab(0)
                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)

                selectPageThroughTab(2)
                assertEquals(messageError, viewPager2.currentItem, tabLayout.selectedTabPosition)
            }
        }
    }

    @Test
    fun test03_checkingTabClick() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {

                fakeListNames.indices.forEach { index ->
                    selectPageThroughTab(index)
                    PageScreen(this@Stage4UnitTest).apply {
                        val caseDescription = "After selecting tab with index $index"
                        assertTabTextContent(pageIndex)
                        assertPageTextContent(
                                caseDescription = caseDescription,
                                expectedName = fakeListNames[pageIndex],
                                expectedDescription = fakeListDescriptions[pageIndex]
                        )
                        assertPageImageRequest(
                                caseDescription = caseDescription,
                                imageUrls = fakeListImageUrls,
                                pageIndex = pageIndex
                        )
                        assertImageViewScale()
                    }
                }
            }
        }
    }
}