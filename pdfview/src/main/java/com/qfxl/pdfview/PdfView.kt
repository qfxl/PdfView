package com.qfxl.pdfview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.RelativeLayout
import java.io.File
import java.io.FileOutputStream


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   : pdfView,combine with PdfRenderer and RecyclerView.
 *     version: 1.0
 * </pre>
 */
class PdfView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attr, defStyleAttr) {

    companion object {
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        const val VERTICAL = LinearLayoutManager.VERTICAL
    }

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
                HORIZONTAL -> pdfRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                VERTICAL -> pdfRecyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    /**
     * pdfLoadTask
     */
    private var pdfLoadTask: PdfLoadTask? = null
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
                pdfLoadTask = PdfLoadTask(pdfLoadListener).also {
                    it.execute(destFile)
                }
            }
        }
    /**
     * pdfFile
     */
    var pdfFile: File? = null
        set(value) {
            if (value != null) {
                pdfLoadTask = PdfLoadTask(pdfLoadListener).also {
                    it.execute(value)
                }
            }
        }

    private var pdfLoadListener: OnPdfLoadListener = object : OnPdfLoadListener {
        /**
         * preLoad
         */
        override fun onPreLoad() {

        }

        /**
         * loading
         */
        override fun onLoading(page: Int, pageCount: Int) {

        }

        /**
         * finish
         */
        override fun onFinish(bitmapPool: BitmapPool) {
            val pdfAdapter = PdfViewAdapter(bitmapPool)
            pdfRecyclerView.adapter = pdfAdapter
        }
    }

    init {
        pdfRecyclerView.layoutManager = LinearLayoutManager(context, orientation, false)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        pdfViewSnapHelper.attachToRecyclerView(pdfRecyclerView)
        addView(pdfRecyclerView, lp)
    }

    fun setOnPdfLoadListener(preload: () -> Unit, loading: (page: Int, pageCount: Int) -> Unit, finish: () -> Unit) {
        pdfLoadListener = object : OnPdfLoadListener {
            override fun onPreLoad() {
                preload()
            }

            override fun onLoading(page: Int, pageCount: Int) {
                loading(page, pageCount)
            }

            override fun onFinish(bitmapPool: BitmapPool) {
                finish()
                val pdfAdapter = PdfViewAdapter(bitmapPool)
                pdfRecyclerView.adapter = pdfAdapter
            }
        }
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
        pdfLoadTask?.cancel(true)
    }

    interface OnPdfLoadListener {
        /**
         * preLoad
         */
        fun onPreLoad()

        /**
         * loading
         */
        fun onLoading(page: Int, pageCount: Int)

        /**
         * finish
         */
        fun onFinish(bitmapPool: BitmapPool)
    }
}