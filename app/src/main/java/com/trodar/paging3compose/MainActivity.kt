package com.trodar.paging3compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.trodar.paging3compose.navigation.MovieNavHost
import com.trodar.paging3compose.ui.theme.Paging3ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Paging3ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    MovieNavHost(
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}
