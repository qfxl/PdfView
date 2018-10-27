package com.qfxl.pdfview

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.qfxl.pdfview.bitmap.BitmapPool
import java.io.File
import java.io.FileOutputStream


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PdfView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attr, defStyleAttr) {

    private val pdfRecyclerView = RecyclerView(context)
    /**
     * pdfSnapHelper extends PagerSnapHelper
     */
    private var pdfViewSnapHelper = PdfViewSnapHelper()
    /**
     * onPdfSelectedListener
     */
    private var onPdfSelectedListener: PdfViewSnapHelper.OnPdfItemSelectListener? = null

    /**
     * pdf show orientation
     */
    var orientation = LinearLayoutManager.VERTICAL
        set(value) {
            when (value) {
                LinearLayoutManager.HORIZONTAL -> pdfRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                else -> pdfRecyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    /**
     * assetsName
     */
    var assetsName: String? = null
        set(value) {
            if (value != null) {
                val destFile = File(context.cacheDir, value)
                if (!destFile.exists()) {
                    val asset = context.assets.open(value)
                    val output = FileOutputStream(destFile)
                    val buffer = ByteArray(1024)
                    var size = asset.read(buffer)
                    while (size != -1) {
                        output.write(buffer, 0, size)
                        size = asset.read(buffer)
                    }
                    asset.close()
                    output.close()
                }
                loadPdfFile(destFile)
            }
        }
    /**
     * pdfFile
     */
    var pdfFile: File? = null
        set(value) {
            if (value != null) {
                loadPdfFile(value)
            }
        }

    private lateinit var bitmapPool: BitmapPool

    private lateinit var pdfRender: PdfRenderer

    init {
        pdfRecyclerView.layoutManager = LinearLayoutManager(context, orientation, false)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        pdfViewSnapHelper.attachToRecyclerView(pdfRecyclerView)
        addView(pdfRecyclerView, lp)
    }

    /**
     * loadFile
     */
    private fun loadPdfFile(pdfFile: File) {
        val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRender = PdfRenderer(fileDescriptor)
        bitmapPool = BitmapPool(pdfRender.pageCount)

        for (index in 0 until pdfRender.pageCount) {
            val page = pdfRender.openPage(index)
            val bitmap = BitmapPool.createBitmap(page.width, page.height)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmapPool.addBitmap(bitmap)
            page.close()
        }

        val pdfAdapter = PdfViewAdapter(bitmapPool)
        pdfRecyclerView.adapter = pdfAdapter
    }

    /**
     * listen pdf item select
     */
    fun setOnPdfSelectListener(action: (position: Int) -> Unit) {
        onPdfSelectedListener = object : PdfViewSnapHelper.OnPdfItemSelectListener {
            /**
             * onItemSelected
             */
            override fun onItemSelected(position: Int) {
                action(position)
            }
        }
        pdfViewSnapHelper.onPdfItemSelectListener = onPdfSelectedListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pdfRender.close()
    }
}