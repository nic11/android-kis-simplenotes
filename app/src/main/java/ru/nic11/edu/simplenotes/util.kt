package ru.nic11.edu.simplenotes

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView

fun putPicture(image: ImageView, pictureB64: String?) {
    if (pictureB64 != null) {
        val byteArray = Base64.decode(pictureB64, Base64.DEFAULT)
        image.setImageBitmap(
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
    } else {
        image.visibility = ImageView.GONE
    }
}
