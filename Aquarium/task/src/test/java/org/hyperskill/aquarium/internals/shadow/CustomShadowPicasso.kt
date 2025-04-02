package org.hyperskill.aquarium.internals.shadow

import android.content.Context
import android.net.Uri
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import com.squareup.picasso.RequestCreator
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject

// Version 1.1
@Suppress("UNUSED", "UNUSED_PARAMETER")
@Implements(Picasso::class)
class CustomShadowPicasso {

    companion object {

        private var _requestMap:  MutableMap<String, RequestCreator> = mutableMapOf()
        val requestMap: Map<String, RequestCreator>
            get() = _requestMap

        fun setSingletonNull() {
            val field = Picasso::class.java.getDeclaredField("singleton")
            field.isAccessible = true
            field.set(null, null)
        }

        fun setContext(context: Context) {
            val field = PicassoProvider::class.java.getDeclaredField("context")
            field.isAccessible = true
            field.set(null, context)
        }

        fun clearRequests() {
            _requestMap.clear()
        }
    }

    @RealObject
    lateinit var picasso: Picasso

    @Implementation
    fun load(uri: Uri): RequestCreator {

        val constructor = RequestCreator::class.java.getDeclaredConstructor(
            Picasso::class.java,
            Uri::class.java,
            Int::class.java
        )
        constructor.isAccessible = true
        val requestCreator = constructor.newInstance(picasso, uri, 0)

        _requestMap[uri.toString()] = requestCreator

        return requestCreator
    }
}