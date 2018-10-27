package com.zbycorp.pdfviewproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qfxl.pdfview.PdfView


class MainActivity : AppCompatActivity() {
    private val FILE_NAME = "sample.pdf"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pdfView = findViewById<PdfView>(R.id.rv_pdf)
        title = "page 0"
        pdfView.assetsName = FILE_NAME
        pdfView.setOnPdfSelectListener {
            title = "page $it"
        }
    }
}
