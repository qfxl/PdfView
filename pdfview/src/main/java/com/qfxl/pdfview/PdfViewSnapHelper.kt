package com.qfxl.pdfview
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PdfViewSnapHelper : PagerSnapHelper() {

    var onPdfItemSelectListener: OnPdfItemSelectListener? = null

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        val targetPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        onPdfItemSelectListener?.onItemSelected(targetPosition)
        return targetPosition
    }

    interface OnPdfItemSelectListener {
        /**
         * onItemSelected
         */
        fun onItemSelected(position: Int)
    }
}