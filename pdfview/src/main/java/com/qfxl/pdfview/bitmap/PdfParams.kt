package com.qfxl.pdfview.bitmap
import android.graphics.Bitmap

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   : PdfParams
 *     version: 1.0
 * </pre>
 */
data class PdfParams(val width: Int, val height: Int, val pageSize: Int, val config: Bitmap.Config = Bitmap.Config.ARGB_8888)