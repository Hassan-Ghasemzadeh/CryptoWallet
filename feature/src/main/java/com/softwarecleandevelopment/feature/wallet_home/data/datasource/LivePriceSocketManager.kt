package com.softwarecleandevelopment.feature.wallet_home.data.datasource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

class LivePriceSocketManager @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private var webSocket: WebSocket? = null
    private val _prices = MutableStateFlow<Map<String, Double>>(emptyMap())
    val prices: StateFlow<Map<String, Double>> = _prices.asStateFlow()
    private var currentAssets: Set<String> = emptySet()

    fun connectForAssets(assets: Set<String>) {
        if (assets == currentAssets && webSocket != null) return
        webSocket?.cancel()
        currentAssets = assets
        if (assets.isEmpty()) {
            _prices.tryEmit(emptyMap())
            return
        }
        val assetsParam = assets.joinToString(",")
        val url = "wss://ws.coincap.io/prices?assets=$assetsParam"
        val request = Request.Builder().url(url).build()
        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                //connected
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                try {
                    val json = JSONObject(text)
                    val newUpdates = mutableMapOf<String, Double>()
                    val keys = json.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value = json.optString(key)
                        val double = value.toDoubleOrNull()
                        if (double != null) {
                            newUpdates[key] = double
                        }
                        if (newUpdates.isNotEmpty()) {
                            _prices.value.toMutableMap().apply {
                                putAll(newUpdates)
                            }.let { merged ->
                                _prices.tryEmit(merged)
                            }
                        }

                    }
                } catch (_: Throwable) {

                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                _prices.tryEmit(emptyMap())
            }

        })
    }

    fun disconnect() {
        webSocket?.close(1000, "closing")
        webSocket = null
        currentAssets = emptySet()
        _prices.tryEmit(emptyMap())
    }
}