package com.qfxl.pdfview

import android.graphics.pdf.PdfRenderer
import android.os.AsyncTask
import android.os.ParcelFileDescriptor
import java.io.File

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/30
 *     desc   : PdfLoad Task
 *     version: 1.0
 * </pre>
 */
class PdfLoadTask(private val onPdfLoadListener: PdfView.OnPdfLoadListener?) : AsyncTask<File, Int, BitmapPool>() {
    private lateinit var pdfRender: PdfRenderer
    private lateinit var bitmapPool: BitmapPool
    /**
     * MainThread
     */
    override fun onPreExecute() {
        super.onPreExecute()
        onPdfLoadListener?.onPreLoad()
    }

    override fun doInBackground(vararg params: File): BitmapPool {
        val pdfFile = params[0]
        val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRender = PdfRenderer(fileDescriptor)
        bitmapPool = BitmapPool(pdfRender.pageCount)
        val pageCount = pdfRender.pageCount
        for (index in 0 until pageCount) {
            val page = pdfRender.openPage(index)
            val bitmap = BitmapPool.createBitmap(page.width, page.height)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmapPool.addBitmap(bitmap)
            page.close()
            publishProgress(index, pageCount)
        }
        return bitmapPool
    }

    /**
     * MainThread
     */
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        onPdfLoadListener?.onLoading(values[0]!!, values[1]!!)
    }

    /**
     * MainThread
     */
    override fun onPostExecute(result: BitmapPool) {
        super.onPostExecute(result)
        pdfRender.close()
        onPdfLoadListener?.onFinish(result)
    }

    override fun onCancelled() {
        super.onCancelled()
        pdfRender.close()
        bitmapPool.releaseAll()
    }

}
