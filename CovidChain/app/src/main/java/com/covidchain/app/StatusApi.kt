package com.covidchain.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "http://0.0.0.0:80/"
data class KeyPair(val publicKey: String, val privateKey: String)
class StatusApi {
    private suspend fun request(url: String, method: String, body: String?): String {
        val result = withContext(Dispatchers.IO) {
            val conn: HttpURLConnection = URL(BASE_URL + url).openConnection() as HttpURLConnection
            conn.requestMethod = method
            body?.let {
                conn.outputStream.write(body.toByteArray())
            }
            conn.connect()

            val inputStream = conn.inputStream
            inputStream
        }
        return result.toString()
    }

    private suspend fun get(url: String): String {
        return request(url, "GET", null)
    }

    private suspend fun post(url: String, body: String): String {
        return request(url, "POST", body)
    }

    suspend fun getKeys(): KeyPair {
        val result = JSONObject(get("getnewaddress"))
        return KeyPair(result.getString("pubkey"), result.getString("privkey"))
    }

    suspend fun publish(keys: KeyPair, target: String): String {
        val body = JSONObject()
        body.put("address", target)
        body.put("privkey", keys.privateKey)
        return post("sendtoaddress/" + keys.publicKey, body.toString())
    }

    suspend fun receive(publicKey: String): String {
        return get("receive")
    }
}