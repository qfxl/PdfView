package com.qfxl.pdfview
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chrisbanes.photoview.PhotoView


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/10/27
 *     desc   : pdfView Adapter
 *     version: 1.0
 * </pre>
 */
class PdfViewAdapter(private val bitmapPool: BitmapPool) : RecyclerView.Adapter<PdfViewAdapter.PdfViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): PdfViewHolder {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.item_pdf_view, container, false)
        return PdfViewHolder(itemView)
    }

    override fun getItemCount(): Int = bitmapPool.size()

    override fun onBindViewHolder(viewHolder: PdfViewHolder, position: Int) {
        viewHolder.pdfPhotoView.setImageBitmap(bitmapPool.getBitmap(position))
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        bitmapPool.releaseAll()
    }

    class PdfViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pdfPhotoView: PhotoView = view.findViewById(R.id.iv_pdf)
    }
}

