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
class Stage3UnitTest : AquariumTest<MainActivity>(MainActivity::class.java) {

    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }


    @Test
    fun test00_checkViewPager() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                viewPager2
            }
        }
    }

    @Test
    fun test01_whenOnPageZeroCheckNextOnce() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "After app init"
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

                nextPage()

                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "While on initial page going to next page"
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

    @Test
    fun test02_whenOnPageOneCheckPreviousOnce() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "After app init"
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

                nextPage()

                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "While on initial page going to next page"
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

                previousPage()

                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "While on page 1 going to previous page"
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

    @Test
    fun test02_whenOnPageZeroCheckNextUntilLastPage() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "After app init"
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

                repeat(fakeListNames.size - 1) {
                    val pageBeforeChange = pageIndex

                    nextPage()

                    PageScreen(this@Stage3UnitTest).apply {
                        val caseDescription = "While on page $pageBeforeChange going to next page"
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

    @Test
    fun test03_whenOnLastPageCheckPreviousUntilFirstPage() {
        testActivity(arguments = stage2Args) {
            MainScreenWithViewPager2(this, initViews = false).apply {
                PageScreen(this@Stage3UnitTest).apply {
                    val caseDescription = "After app init"
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

                repeat(fakeListNames.size - 1) {
                    val pageBeforeChange = pageIndex

                    nextPage()

                    PageScreen(this@Stage3UnitTest).apply {
                        val caseDescription = "While on page $pageBeforeChange going to next page"
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

                repeat(fakeListNames.size - 1) {
                    val pageBeforeChange = pageIndex

                    previousPage()

                    PageScreen(this@Stage3UnitTest).apply {
                        val caseDescription = "While on page $pageBeforeChange going to previous page"
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