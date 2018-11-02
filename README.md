# PdfView
 
Android加载pdf的一种方案，采用了系统的[PdfRenderer][1]结合了RecyclerView跟[PhotoView][2]。

## 效果图
![此处输入图片的描述][3]
![此处输入图片的描述][4]
## Gradle
> implementation 'com.qfxl:pdfview:1.0.0-alpha'

## code
```kotlin
val pdfView = findViewById(R.id.rv_pdf)
pdfView.assetsName = FILE_NAME
```

或者

```kotlin
val pdfView = findViewById(R.id.rv_pdf)
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
```

`持续更新中`


  [1]: https://developer.android.google.cn/reference/android/graphics/pdf/PdfRenderer
  [2]: https://github.com/chrisbanes/PhotoViewerence/android/graphics/pdf/PdfRenderer
  [3]: https://github.com/qfxl/PdfView/blob/master/gif/basic.gif
  [4]: https://github.com/qfxl/PdfView/blob/master/gif/complex.gif
