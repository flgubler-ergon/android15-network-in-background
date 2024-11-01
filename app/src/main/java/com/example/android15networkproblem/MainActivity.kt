package com.example.android15networkproblem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android15networkproblem.ui.theme.Android15NetworkProblemTheme
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android15NetworkProblemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.safeContentPadding().padding(20.dp)) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Button(
                            onClick = { (CoroutineScope(Dispatchers.Default)).launch { pollServer(OkHttpClient()) } },
                        ) { Text("Start Polling") }
                    }
                }
            }
        }
    }

    private suspend fun pollServer(client: OkHttpClient) {
        val request = Request.Builder()
            .url("https://www.google.com")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                if (responseBody == null) {
                    Log.w("PollingTest", "No polling response available")
                } else {
                    Log.i("PollingTest", "Received polling response at ${LocalDateTime.now()}")
                }
            }
        } catch (e: Exception) {
            Log.e("PollingTest", "Failed to get polling response at ${LocalDateTime.now()}", e)
            e.printStackTrace()
        }

        delay(5000)
        pollServer(client)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android15NetworkProblemTheme {
        Greeting("Android")
    }
}