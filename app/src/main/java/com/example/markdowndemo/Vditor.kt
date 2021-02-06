package com.example.markdowndemo

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.webkit.*


/**
 * 自定义MD编辑器
 *
 * @author qq1962247851
 * @date 2021/2/2 13:44
 */
class Vditor : WebView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        privateBrowsing: Boolean
    ) : super(context, attrs, defStyleAttr, privateBrowsing)

    init {
        settings.apply {
            javaScriptEnabled = true
            // 设置可以访问文件
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowContentAccess = true
            domStorageEnabled = true
            allowFileAccessFromFileURLs = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canGoBack()) {
                goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun startLoad(isEditMode: Boolean, isDarkTheme: Boolean, i: IVditorCallback) {
        this.isEditMode = isEditMode
        this.isDarkTheme = isDarkTheme
        this.i = i
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            webViewClient = object : WebViewClient() {

//                override fun shouldInterceptRequest(
//                    view: WebView?,
//                    request: WebResourceRequest?
//                ): WebResourceResponse? {
//                    if (request != null && request.url != null) {
//                        var requestUrl = request.url.toString()
//                        XLog.d("${TAG}shouldInterceptRequest，requestUrl：$requestUrl")
////                        val scheme = request.url.scheme!!.trim()
////                        if (scheme.equals("http", true) ||
////                            scheme.equals("https", true)
////                        ) {
//                        if (requestUrl.startsWith("file://")) {
//                            requestUrl = requestUrl.replace("file://", "https://")
//                        }
//                        val url = URL(requestUrl)
//                        val urlConnection = url.openConnection()
//                        val conn = urlConnection as HttpURLConnection
//                        conn.setRequestProperty("Accept-Charset", "utf-8")
//                        conn.setRequestProperty("contentType", "utf-8")
//                        conn.requestMethod = "GET"
//
//
//                        // Read input
//                        val charset =
//                            if (conn.contentEncoding != null) conn.contentEncoding else Charset.defaultCharset()
//                                .displayName()
//                        val mime = conn.contentType
////                        var pageContents: ByteArray = conn.inputStream.readBytes()
//
////                        // Perform JS injection
////                        if (mime == "text/html") {
////                            pageContents = PostInterceptJavascriptInterface
////                                .enableIntercept(mContext, pageContents)
////                                .getBytes(charset)
////                        }
//
//                        // Convert the contents and return
////                        val isContents: InputStream = ByteArrayInputStream(pageContents)
//
//                        return WebResourceResponse(
//                            mime, charset, ByteArrayInputStream(conn.inputStream.readBytes())
//                        )
//                    } else {
//                        return super.shouldInterceptRequest(view, request)
//                    }
//                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (request != null && request.url != null) {
                        var url = request.url.toString()
                        XLog.d("${TAG}shouldOverrideUrlLoading，url：$url")
//                        val scheme = request.url.scheme!!.trim()
//                        if (scheme.equals("http", true) ||
//                            scheme.equals("https", true)
//                        ) {
                        return super.shouldOverrideUrlLoading(view, object : WebResourceRequest {
                            override fun getUrl(): Uri {
                                if (url.startsWith("file://player.bilibili.com/")) {
                                    url = url.replace("file://", "https://")
                                }
                                return Uri.parse(url)
                            }

                            override fun isForMainFrame(): Boolean {
                                return request.isForMainFrame
                            }

                            override fun isRedirect(): Boolean {
                                return request.isRedirect
                            }

                            override fun hasGesture(): Boolean {
                                return request.hasGesture()
                            }

                            override fun getMethod(): String {
                                return request.method
                            }

                            override fun getRequestHeaders(): Map<String, String> {
                                return request.requestHeaders
                            }
                        })
//                        }
                    } else {
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.d(TAG, "onReceivedError，url=${request?.url}，error=${error?.description}")
                    super.onReceivedError(view, request, error)
                }
            }
        }
        addJavascriptInterface(VditorInterface(), "android")
        loadUrl("file:///android_asset/vditor/index.html?isEditMode=$isEditMode&isDarkTheme=$isDarkTheme")
    }

    fun initEditor(key: String) {
        loadUrl("javascript:initEditor(\"$key\")")
        XLog.d(TAG + "javascript:initEditor(\"$key\")")
    }

    fun initPreviewer(content: String) {
        this.content = content
        loadUrl("javascript:initPreviewer(\"${getEscapedString(content)}\")")
        XLog.d(TAG + "javascript:initPreviewer(\"$content\")")
    }

    lateinit var i: IVditorCallback
    var isEditMode = false
    var isDarkTheme = false
    var content: String = ""

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        this.isDarkTheme = isDarkTheme
        loadUrl("javascript:setIsDarkTheme($isDarkTheme)")
    }

    fun setLightTheme() {
        isDarkTheme = false
        loadUrl("javascript:setLightTheme()")
    }

    fun setDarkTheme() {
        isDarkTheme = true
        loadUrl("javascript:setDarkTheme()")
    }

    fun insertPicture(url: String, alt: String, title: String) {
        loadUrl("javascript:insertPicture(\"$url\",\"$alt\",\"$title\")")
    }

    fun clearCache(key: String) {
        loadUrl("javascript:clearCache(\"$key\")")
    }

    fun setCache(key: String, value: String) {
        XLog.d(TAG + "setCache，$key，$value")
        loadUrl(
            "javascript:setCache(\"$key\",\"${getEscapedString(value)}\")"
        )
        XLog.d(TAG + "setCache，$key，${getEscapedString(value)}")
    }

    fun bold() {
        loadUrl("javascript:clickToolbarBold()")
    }

    fun italic() {
        loadUrl("javascript:clickToolbarItalic()")
    }

    fun strike() {
        loadUrl("javascript:clickToolbarStrike()")
    }

    fun link() {
        loadUrl("javascript:clickToolbarLink()")
    }

    fun list() {
        loadUrl("javascript:clickToolbarList()")
    }

    fun orderedList() {
        loadUrl("javascript:clickToolbarOrderedList()")
    }

    fun checkList() {
        loadUrl("javascript:clickToolbarCheckList()")
    }

    fun outdent() {
        loadUrl("javascript:clickToolbarOutdent()")
    }

    fun indent() {
        loadUrl("javascript:clickToolbarIndent()")
    }

    fun quote() {
        loadUrl("javascript:clickToolbarQuote()")
    }

    fun line() {
        loadUrl("javascript:clickToolbarLine()")
    }

    fun code() {
        loadUrl("javascript:clickToolbarCode()")
    }

    fun inlineCode() {
        loadUrl("javascript:clickToolbarInlineCode()")
    }

    fun insertBefore() {
        loadUrl("javascript:clickToolbarInsertBefore()")
    }

    fun insertAfter() {
        loadUrl("javascript:clickToolbarInsertAfter()")
    }

    fun table() {
        loadUrl("javascript:clickToolbarTable()")
    }

    fun undo() {
        loadUrl("javascript:clickToolbarUndo()")
    }

    fun redo() {
        loadUrl("javascript:clickToolbarRedo()")
    }

    fun both() {
        loadUrl("javascript:clickToolbarBoth()")
    }

    fun codeTheme() {
        loadUrl("javascript:clickToolbarCodeTheme()")
    }

    fun contentTheme() {
        loadUrl("javascript:clickToolbarContentTheme()")
    }

    fun export() {
        loadUrl("javascript:clickToolbarExport()")
    }

    fun outline() {
        loadUrl("javascript:clickToolbarOutline()")
    }

    fun preview() {
        loadUrl("javascript:clickToolbarPreview()")
    }

    fun devtools() {
        loadUrl("javascript:clickToolbarDevtools()")
    }

    fun info() {
        loadUrl("javascript:clickToolbarInfo()")
    }

    fun help() {
        loadUrl("javascript:clickToolbarHelp()")
    }

    fun editMode() {
        loadUrl("javascript:clickToolbarEditMode()")
    }

    fun headingLevel1() {
        loadUrl("javascript:clickToolbarHeadingLevel1()")
    }

    fun headingLevel2() {
        loadUrl("javascript:clickToolbarHeadingLevel2()")
    }

    fun headingLevel3() {
        loadUrl("javascript:clickToolbarHeadingLevel3()")
    }

    fun headingLevel4() {
        loadUrl("javascript:clickToolbarHeadingLevel4()")
    }

    fun headingLevel5() {
        loadUrl("javascript:clickToolbarHeadingLevel5()")
    }

    fun headingLevel6() {
        loadUrl("javascript:clickToolbarHeadingLevel6()")
    }


    inner class VditorInterface {
        /**
         * 使用js获取获取MD格式的文本
         */
        @JavascriptInterface
        fun getValue(value: String) {
            content = value
            post {
                i.onChange(value)
            }
        }

        /**
         * 初始化完成
         */
        @JavascriptInterface
        fun onLoadFinished() {
            post {
                i.onLoadFinished()
            }
        }

        /**
         * 输入后
         */
        @JavascriptInterface
        fun onChange(value: String) {
            content = value
            XLog.d("$TAG$value")
            i.onChange(value)
        }

        /**
         * 选择图片
         */
        @JavascriptInterface
        fun selectPicture() {
            post {
                i.onSelectPicture()
            }
        }
<<<<<<< HEAD

        /**
         * 查看图片大图
         */
        @JavascriptInterface
        fun onPictureClick(imgUrls: String, position: Int) {
            XLog.d("$TAG$imgUrls,$position")
            post {
                //i.onPictureClick(imgUrls, position)
            }
        }
=======
>>>>>>> 6e7c02ee7714c1653227ac5f52315886767cbcb7
    }

    interface IVditorCallback {
        /**
         * 选择图片
         */
        @Deprecated("这个本来是自定义vditor工具栏的时候设置的回调，然后调用选择图片然后insertPicture()，现在把vditor自带的工具栏隐藏了，这个回调就用不到了，直接insertPicture就行了")
        fun onSelectPicture()

        /**
         * 初始化完成
         */
        fun onLoadFinished()

        /**
         * 内容变化
         */
        fun onChange(value: String)
<<<<<<< HEAD

        /**
         * 查看大图
         */
        fun onPictureClick(imgUrls: String, position: Int)
=======
>>>>>>> 6e7c02ee7714c1653227ac5f52315886767cbcb7
    }

    companion object {
        const val TAG = "Vditor："
    }

    private fun getEscapedString(input: String?): String {
        return if (input.isNullOrEmpty()) {
            ""
        } else {
            input.replace("\n", "\\n")
                .replace("\"", "\\\"")
        }
    }

}