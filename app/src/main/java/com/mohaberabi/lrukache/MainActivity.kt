package com.mohaberabi.lrukache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.lrukache.ui.theme.LRUKacheTheme

class MainActivity : ComponentActivity() {


    private val viewmodel by viewModels<LruViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            LRUKacheTheme {

                HomeScreen(
                    viewModel = viewmodel,
                )
            }
        }
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: LruViewModel
) {


    val hostState = SnackbarHostState()
    LaunchedEffect(
        key1 = Unit,
    ) {

        viewModel.channel.collect { event ->
            when (event) {
                LRUResult.HIT -> hostState.showSnackbar("It is a hit")
                LRUResult.MISS -> hostState.showSnackbar("It is a miss")
            }
        }
    }

    var key by remember {

        mutableStateOf("")
    }

    var value by remember {

        mutableStateOf("")
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = hostState) }
    ) {

            padding ->

        Column(
            modifier = modifier
                .padding(padding)
                .padding(8.dp)
        ) {

            TextField(value = key, onValueChange = { key = it })
            Spacer(modifier = Modifier.height(200.dp))
            TextField(value = value, onValueChange = { value = it })
            Button(
                onClick = {
                    viewModel.add(key, value)

                },
            ) {
                Text(text = "Add Data")
            }
        }

    }
}