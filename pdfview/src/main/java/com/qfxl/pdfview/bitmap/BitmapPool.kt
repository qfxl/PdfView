package com.qfxl.pdfview.bitmap

import android.graphics.Bitmap

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   : bitmapPool
 *     version: 1.0
 * </pre>
 */
class BitmapPool(size: Int) {

    private val bitmaps = ArrayList<Bitmap?>(size)

    fun addBitmap(bitmap: Bitmap) {
        if (bitmaps.contains(bitmap)) {
            bitmaps.add(bitmaps.indexOf(bitmap), bitmap)
        } else {
            bitmaps.add(bitmap)
        }
    }

    fun getBitmap(position: Int) = bitmaps[position]

    fun remove(position: Int) {
        releaseBitmap(position)
    }

    fun releaseAll() {
        for (index in 0 until bitmaps.size) {
            releaseBitmap(index)
        }
    }

    companion object {
        fun createBitmap(width: Int, height: Int, config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap = Bitmap.createBitmap(width, height, config)
    }

    fun size() = bitmaps.size

    private fun releaseBitmap(position: Int) {
        bitmaps[position]?.recycle()
        bitmaps[position] = null
    }
}