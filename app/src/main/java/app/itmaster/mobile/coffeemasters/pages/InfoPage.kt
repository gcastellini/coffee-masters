package app.itmaster.mobile.coffeemasters.pages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


@Composable
fun InfoPage() {
    val vm: InfoViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient =
                    WebViewClient() // permite que ahora todos los links se sigan dentro de la app
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

                val jsInterface = MyJsInterface(it, vm)
                addJavascriptInterface(jsInterface, "Android")

                loadUrl("file:///android_asset/index.html")


            }
        })
    if (vm.showSnackbar.value) {
        scope.launch {

            Log.d("show", (vm.showSnackbar.value).toString())
            snackbarHostState.showSnackbar("Thank you for your feedback")
            vm.onSnackbarShown()

        }
        SnackbarHost(hostState = snackbarHostState)

    }
}

class MyJsInterface(private val mContext: Context,private val viewModel: InfoViewModel) {

    @JavascriptInterface
    fun getAndroidVersion(): Int {
        return Build.VERSION.SDK_INT
    }


    @JavascriptInterface
    fun showToast(){
        viewModel.onShowSnackbar()
        Log.d("what",viewModel.showSnackbar.value.toString())

    }


}

class InfoViewModel : ViewModel() {

    val showSnackbar: MutableState<Boolean> = mutableStateOf(false)

    fun onShowSnackbar() {
        showSnackbar.value=true
    }

    fun onSnackbarShown() {
        showSnackbar.value = false
    }
}