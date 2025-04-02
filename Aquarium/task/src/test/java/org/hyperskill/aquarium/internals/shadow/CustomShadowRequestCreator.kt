package org.hyperskill.aquarium.internals.shadow

import android.app.Notification
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow
import org.robolectric.util.ReflectionHelpers.ClassParameter

@Suppress("UNUSED", "UNUSED_PARAMETER")
@Implements(RequestCreator::class)
class CustomShadowRequestCreator {

    @RealObject
    lateinit var requestCreator: RequestCreator
    var imageViewLoaded : ImageView? = null
    var callback : Callback? = null
    var placeholderId : Int? = null
    var errorId : Int? = null

    @Implementation
    fun into(imageView: ImageView, callback: Callback?) {
        imageViewLoaded = imageView
        this.callback = callback
    }

    @Implementation
    fun into(remoteViews: RemoteViews, @IdRes viewId: Int, appWidgetIds: IntArray, callback: Callback?) {

        throw AssertionError(
            "method .into() with RemoteViews argument " +
                    "is not supported for this project tests"
        )
    }

    @Implementation
    fun into(
        remoteViews: RemoteViews, @IdRes viewId: Int, notificationId: Int,
        notification: Notification, notificationTag: String?, callback: Callback?,
    ) {
        throw AssertionError(
            "method .into() with RemoteViews argument " +
                    "is not supported for this project tests"
        )
    }

    @Implementation
    fun into(target: Target) {
        throw AssertionError(
            "method .into() with Target argument " +
                    "is not supported for this project tests"
        )
    }

    @Implementation
    fun placeholder(@DrawableRes placeholderResId: Int): RequestCreator {
        // calling real placeholder
        val parameter = ClassParameter.from(Int::class.java, placeholderResId)
        Shadow.directlyOn<RequestCreator, RequestCreator>(
            requestCreator,
            requestCreator.javaClass,
            "placeholder",
            parameter
        )

        placeholderId = placeholderResId
        return requestCreator
    }

    fun placeholder(placeholderDrawable: Drawable): RequestCreator {
        val placeholderId = shadowOf(placeholderDrawable).createdFromResId
        return requestCreator.placeholder(placeholderId)
    }

    fun error(@DrawableRes errorResId: Int): RequestCreator {
        // calling real error
        val parameter = ClassParameter.from(Int::class.java, errorResId)
        Shadow.directlyOn<RequestCreator, RequestCreator>(
            requestCreator,
            requestCreator.javaClass,
            "error",
            parameter
        )
        errorId = errorResId
        return requestCreator
    }

    fun error(errorDrawable: Drawable): RequestCreator {
        val errorId = shadowOf(errorDrawable).createdFromResId
        return requestCreator.error(errorId)
    }
}