package app.itmaster.mobile.coffeemasters.pages

import android.os.Build
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView



@Composable
fun InfoPage() {

    AndroidView(
        factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient() // permite que ahora todos los links se sigan dentro de la app
            webChromeClient = object : WebChromeClient() {
                    override fun onJsAlert(
                        view: WebView,
                        url: String,
                        message: String,
                        result: JsResult
                    ): Boolean {
                        println("Alerta de JS: ${message}")
                        return true
                    }
                }

            val jsInterface = MyJsInterface(this)
            addJavascriptInterface(jsInterface, "Android")

            loadUrl("file:///android_asset/index.html")


        }
    })
}

class MyJsInterface(private val mContext: WebView) {
    @JavascriptInterface
    fun getAndroidVersion(): Int {
        return Build.VERSION.SDK_INT
    }

}