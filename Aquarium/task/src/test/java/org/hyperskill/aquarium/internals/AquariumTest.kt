package org.hyperskill.aquarium.internals

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.tabs.TabLayout
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListDescriptions
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListImageResources
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListImageUrls
import org.hyperskill.aquarium.internals.data.AquariumFakeData.fakeListNames
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.robolectric.Shadows.shadowOf
import java.util.concurrent.TimeUnit

open class AquariumTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {


    val stage1Args by lazy {
        Intent().apply {
            putExtra("imageAnimals", fakeListImageResources.take(1) as java.io.Serializable)
            putExtra("nameAnimals", fakeListNames.take(1) as java.io.Serializable)
            putExtra("descriptionAnimals", fakeListDescriptions.take(1) as java.io.Serializable)
        }
    }

    val stage2Args by lazy {
        Intent().apply {
            putExtra("imageAnimals", fakeListImageUrls as java.io.Serializable)
            putExtra("nameAnimals", fakeListNames as java.io.Serializable)
            putExtra("descriptionAnimals", fakeListDescriptions as java.io.Serializable)
        }
    }

    internal fun TextView.assertAppearanceMaterialBody1(idString: String) {
        /* android:android:textAllCaps = false     android:android:textSize = 16sp */

        val expectedAllCaps = false
        val expectedSize = 16.0f
        val allCaps = this.isAllCaps
        val textSize = this.textSize
        val errorMessageFmt =
            "expected TextView with id $idString, to be styled with " +
                    "TextAppearance.MaterialComponents.Body1. On property %s"
        assertEquals(errorMessageFmt.format("textAllCaps"), expectedAllCaps, allCaps)
        assertEquals(errorMessageFmt.format("textSize"), expectedSize, textSize)
    }

    internal fun TextView.assertAppearanceMaterialBody2(idString: String) {
        val expectedAllCaps = false
        val expectedSize = 14.0f
        val allCaps = this.isAllCaps
        val textSize = this.textSize
        val errorMessageFmt =
            "expected TextView with id $idString, to be styled with " +
                    "TextAppearance.MaterialComponents.Body2. On property %s"
        assertEquals(errorMessageFmt.format("textAllCaps"), expectedAllCaps, allCaps)
        assertEquals(errorMessageFmt.format("textSize"), expectedSize, textSize)
    }

    internal fun TextView.assertFontFamily(idString: String, expectedFontFamily: String) {
        val errorMessage =
            "expected TextView with id $idString, to be styled with fontFamily"

        val shadowTypeface = shadowOf(typeface)
        val actualFontFamily = shadowTypeface.fontDescription.familyName
        assertEquals(errorMessage, expectedFontFamily, actualFontFamily)
    }

    internal fun TextView.assertTextStyle(idString: String,expectedStyle: Int){
        val errorMessage =
            "expected TextView with id $idString, to be styled with textStyle"
        val shadowTypeface = shadowOf(typeface)
        val actualStyle = shadowTypeface.fontDescription.style
        assertEquals(errorMessage, expectedStyle, actualStyle)
    }

    internal fun CardView.assertCornerRadius(expectedRadius : Float) {
        val radius = this.radius
        val errorMessage = "expected $expectedRadius with CardView, " +
                "but was $radius"
        assertEquals(errorMessage, expectedRadius, radius)

    }
    internal fun Drawable?.assertEquals(
        message: String, expectedResourceId: Int, expectedResourceIdString: String
    ) {

        val shadowDrawable = this?.let { shadowOf(it) }
        val actualResourceId = shadowDrawable?.createdFromResId
        val errorMessage = "$message " +
                "expected $expectedResourceIdString with id $expectedResourceId, " +
                "but was _ with id $actualResourceId"
        Assert.assertTrue(errorMessage, actualResourceId == expectedResourceId)
    }

    internal fun TextView.assertText(
        expectedText: String,
        idString: String,
        caseDescription: String) {

        val actualText = text.toString()
        val message = "$caseDescription, on property text of TextView with id $idString"

        assertEquals(message, expectedText, actualText)
    }

    internal fun TabLayout.clickTabAndRun(index: Int) {
        selectTab(getTabAt(index))
        shadowLooper.idleFor(500, TimeUnit.MILLISECONDS)
    }
    internal fun CardView.assertElevation(expectedElevation:Float){
        val actualElevation = cardElevation
        val message = "CardView Elevation value is"
        assertEquals(message,expectedElevation,actualElevation)
    }
}