package com.zbycorp.pdfviewproject

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.qfxl.pdfview.PdfView
import java.io.File


class MainActivity : AppCompatActivity() {
    private val FILE_NAME = "sample.pdf"
    private lateinit var pdfView: PdfView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pb = ProgressDialog(this)
        pb.setCanceledOnTouchOutside(false)
        pb.setMessage("pdf加载中...")
        pdfView = findViewById(R.id.rv_pdf)
        pdfView.orientation = PdfView.VERTICAL
        title = "page 0"
        pdfView.setOnPdfLoadListener({
            pb.show()
        }, { page, pageCount ->
            pb.setMessage("正在加载第${page+1}页/${pageCount}页")
        }, {
            pb.dismiss()
        })
        pdfView.assetsName = FILE_NAME
        pdfView.setOnPdfSelectListener {
            title = "page $it"
        }


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
//        } else {
//            val pdfFile = File("${Environment.getExternalStorageDirectory()}/Android+Gradle权威指南.pdf")
//            pdfView.pdfFile = pdfFile
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            val pdfFile = File("${Environment.getExternalStorageDirectory()}/Android+Gradle权威指南.pdf")
            pdfView.pdfFile = pdfFile
        }
    }

}
