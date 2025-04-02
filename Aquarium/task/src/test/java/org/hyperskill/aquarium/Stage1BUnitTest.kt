package org.hyperskill.aquarium

import android.content.Intent
import org.hyperskill.aquarium.internals.AquariumTest
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListDescriptions
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListImageUrls
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListNames
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
class Stage1BUnitTest : AquariumTest<MainActivity>(MainActivity::class.java) {

    @Before
    fun setUp() {
        CustomShadowPicasso.setContext(activity)
        CustomShadowPicasso.setSingletonNull()
    }

    @Test
    fun test00_checkCardView() {
        testActivity(arguments = stage2Args) {
            PageScreen(this, initViews = false).apply {
                cardView
            }
        }
    }

    @Test
    fun test01_checkNestedScrollView() {
        testActivity(arguments = stage2Args) {
            PageScreen(this, initViews = false).apply {
                nestedScrollView
            }
        }
    }

    @Test
    fun test02_checkImageView() {
        testActivity(arguments = stage2Args) {
            PageScreen(this, initViews = false).apply {
                imageView

            }
        }
    }

    @Test
    fun test03_checkTvName() {
        testActivity(arguments = stage2Args) {
            PageScreen(this, initViews = false).apply {
                tvName
            }
        }
    }

    @Test
    fun test04_checkTvDescription(){
        testActivity(arguments = stage2Args) {
            PageScreen(this, initViews = false).apply {
                tvDescription
            }
        }
    }

    @Test
    fun test05_checkPageDefaultContent(){
        val images = fakeListImageUrls
        val names = fakeListNames
        val descriptions = fakeListDescriptions


        testActivity {
            PageScreen(this).apply {
                val caseDescription = "When using default content on initial page"
                assertPageTextContent(
                        caseDescription = caseDescription,
                        expectedName = names[0],
                        expectedDescription = descriptions[0]
                )

                assertPageImageRequest(caseDescription, images, 0)
                assertImageViewScale()
            }
        }
    }

    @Test
    fun test06_checkPageCustomContent(){
        val images = fakeListImageUrls.drop(1)
        val names = fakeListNames.drop(1)
        val descriptions = fakeListDescriptions.drop(1)

        val args = Intent().apply {
            putExtra("imageAnimals", images as java.io.Serializable)
            putExtra("nameAnimals", names as java.io.Serializable)
            putExtra("descriptionAnimals", descriptions as java.io.Serializable)
        }

        testActivity(arguments = args) {
            PageScreen(this).apply {
                val caseDescription =
                        "When using custom content passed through intent.extras on initial page"
                assertPageTextContent(
                        caseDescription = caseDescription,
                        expectedName = names[0],
                        expectedDescription = descriptions[0]
                )

                assertPageImageRequest(caseDescription, images, 0)
                assertImageViewScale()
            }
        }
    }
}