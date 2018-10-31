package com.zbycorp.pdfviewproject

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qfxl.pdfview.PdfView

class ComplexUseActivity : AppCompatActivity() {
    companion object {
        private const val FILE_NAME = "sample.pdf"
    }

    private lateinit var pdfView: PdfView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfc)
        val pb = ProgressDialog(this)
        pb.setCanceledOnTouchOutside(false)
        pb.setMessage("pdf加载中...")
        pdfView = findViewById(R.id.rv_pdf)
        pdfView.orientation = PdfView.HORIZONTAL
        title = "page 0"
        pdfView.setOnPdfLoadListener(
                {
                    pb.show()
                },
                { page, pageCount ->
                    pb.setMessage("正在加载第${page + 1}页/${pageCount}页")
                },
                {
                    pb.dismiss()
                })
        pdfView.assetsName = FILE_NAME
        pdfView.setOnPdfSelectListener {
            title = "page $it"
        }
    }
}
