package com.zbycorp.pdfviewproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qfxl.pdfview.PdfView

class BasicUseActivity : AppCompatActivity() {
    private lateinit var pdfView: PdfView

    companion object {
        private const val FILE_NAME = "sample.pdf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfc)
        pdfView = findViewById(R.id.rv_pdf)
        pdfView.assetsName = FILE_NAME
    }
}
