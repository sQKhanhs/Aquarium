package org.hyperskill.aquarium.internals.screen

import android.app.Activity
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import org.hyperskill.aquarium.R
import org.hyperskill.aquarium.internals.AquariumTest
import org.hyperskill.aquarium.internals.shadow.CustomShadowPicasso
import org.hyperskill.aquarium.internals.shadow.CustomShadowRequestCreator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.robolectric.shadow.api.Shadow


class PageScreen<T : Activity>(private val test: AquariumTest<T>, initViews: Boolean = true) {

    companion object {
        const val ID_CARD_VIEW = "card_view"
        const val ID_IMAGE_VIEW = "image_view"
        const val ID_TV_NAME = "tv_name"
        const val ID_TV_DESCRIPTION = "tv_description"
        const val ID_NESTED_SCROLL_VIEW = "scroll_view"
    }

    val cardView: CardView by lazy {
        with(test) {
            activity.findViewByString<CardView>(ID_CARD_VIEW).apply {
                assertCornerRadius(expectedRadius = 15f)
                assertElevation(expectedElevation = 10f)
            }
        }
    }

    val nestedScrollView: NestedScrollView by lazy {
        with(test) {
            cardView.findViewByString(ID_NESTED_SCROLL_VIEW)
        }
    }

    val imageView: ImageView by lazy {
        with(test) {
            cardView.findViewByString(ID_IMAGE_VIEW)
        }
    }

    val tvName: TextView by lazy {
        with(test) {
            cardView.findViewByString<TextView>(ID_TV_NAME).apply {
                assertAppearanceMaterialBody1(ID_TV_NAME)
                assertFontFamily(ID_TV_NAME, "monospace")
                assertTextStyle(ID_TV_NAME, Typeface.BOLD)
            }
        }
    }

    val tvDescription: TextView by lazy {
        with(test) {
            nestedScrollView.findViewByString<TextView>(ID_TV_DESCRIPTION).apply {
                assertAppearanceMaterialBody2(ID_TV_DESCRIPTION)
                assertFontFamily(ID_TV_DESCRIPTION, "sans-serif")
                assertTextStyle(ID_TV_NAME, Typeface.NORMAL)
            }
        }
    }

    init {
        if (initViews) {
            cardView
            tvDescription
            tvName
            imageView
            nestedScrollView
        }
    }


    fun assertImageViewResource(expectedImageId: Int, expectedImageIdString: String) = with(test) {
        imageView.drawable.assertEquals(
            message = "Incorrect drawable set on $ID_IMAGE_VIEW",
            expectedResourceId = expectedImageId,
            expectedResourceIdString = expectedImageIdString
        )
    }

    fun assertImageViewScale() = with(test) {
        val expectedScaleType = ImageView.ScaleType.CENTER_CROP
        val scaleType = imageView.scaleType
        val errorMessage =
            "on $ID_IMAGE_VIEW expected CENTER_CROP with ScaleType, but was ${scaleType.name}"
        assertEquals(errorMessage, expectedScaleType.name, scaleType.name)
    }

    fun assertPageTextContent(
        caseDescription: String,
        expectedName: String,
        expectedDescription: String
    ) = with(test) {
        tvName.assertText(expectedName, ID_TV_NAME, caseDescription)
        tvDescription.assertText(expectedDescription, ID_TV_DESCRIPTION, caseDescription)
    }

    fun assertPageImageRequest(
        caseDescription: String,
        imageUrls: List<String>,
        pageIndex: Int
    ) = with(test) {

        if(pageIndex !in imageUrls.indices) {
            throw IllegalArgumentException(
                "Test tried to make assertion with invalid index. Test needs to be fixed"
            )
        }

        val expectedImageUrl = imageUrls[pageIndex]
        val request = CustomShadowPicasso.requestMap[expectedImageUrl]
        val messageRequestNull = "$caseDescription, expected to be on page $pageIndex " +
                "and to have a request for url $expectedImageUrl"
        assertNotNull(messageRequestNull, request)

        val requestShadow = Shadow.extract<CustomShadowRequestCreator>(request)
        val actualImageView = requestShadow.imageViewLoaded
        val messageImageView =
            "$caseDescription, expected image to be loaded into $ID_IMAGE_VIEW"

        assertEquals(messageImageView, imageView, actualImageView)

        val messagePlaceholderIdError =
            "$caseDescription, expected placeholder to be set with R.drawable.placeholder"
        val expectedPlaceholderId = R.drawable.placeholder
        val actualPlaceholderId = requestShadow.placeholderId
        assertEquals(messagePlaceholderIdError, expectedPlaceholderId, actualPlaceholderId)

        val messageErrorIdError =
            "$caseDescription, expected error to be set with R.drawable.error"
        val expectedErrorId = R.drawable.error
        val actualErrorId = requestShadow.errorId
        assertEquals(messageErrorIdError, expectedErrorId, actualErrorId)
    }
}