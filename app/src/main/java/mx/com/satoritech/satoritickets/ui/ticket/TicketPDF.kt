package mx.com.satoritech.satoritickets.ui.ticket

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.utils.DialogLoading
import mx.com.satoritech.satoritickets.utils.ToolBar

@Composable
fun TicketPdf(
    navController: NavController,
    ticketId: String
){
    ToolBar(
        navController = navController,
        showLogout = false,
        title = stringResource(id = R.string.ticketPDF)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = 35.dp)
//                    .padding(bottom = 10.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//            }
            val url = //"http://192.168.0.6/api/satori/getPdf/$ticketId"
                //"https://docs.google.com/gview?embedded=true&url=${APIConstants.getServerPath()}api/satori/getPdf/$ticketId"
                "https://docs.google.com/gview?embedded=true&url=https://www.gamesdatabase.org/Media/SYSTEM/Microsoft_Xbox//Manual/formated/Halo-_Combat_Evolved_-_Microsoft_Game_Studios.pdf"
            val visibility = remember { mutableStateOf(true) }
            if (visibility.value){
                DialogLoading()
            }
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object: WebViewClient(){
                            override fun onPageStarted(
                                view: WebView, url: String,
                                favicon: Bitmap?) {
                                visibility.value = false
                            }
                            override fun onPageFinished(view: WebView, url: String) {}
                        }
                        loadUrl(url)
                    } }
                , update = {
                    it.loadUrl(url)
                }
            )
        }
    }
}