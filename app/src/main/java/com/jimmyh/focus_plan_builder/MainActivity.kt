package com.jimmyh.focus_plan_builder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jimmyh.focus_plan_builder.ui.theme.Focus_Plan_BuilderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Focus_Plan_BuilderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FocusPlanRoute(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}