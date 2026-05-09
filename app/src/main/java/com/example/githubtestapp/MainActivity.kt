package com.example.githubtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.githubtestapp.presentation.navigation.GithubNavGraph
import com.example.githubtestapp.ui.GithubTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GithubTestAppTheme {
                GithubNavGraph()
            }
        }
    }
}