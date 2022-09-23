package com.solvro.topwr.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object DrawableToBitmapDescriptorConverter {
    fun vectorToBitmap(resources: Resources, @DrawableRes id: Int): BitmapDescriptor? {
        val vector: Drawable? =
            ResourcesCompat.getDrawable(resources, id, null)

        return vector?.let {
            val bitmap = Bitmap.createBitmap(
                vector.intrinsicWidth,
                vector.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vector.setBounds(0, 0, canvas.width, canvas.height)
            vector.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}