package com.cheonjaeung.repro.coil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.cheonjaeung.repro.coil.ui.theme.CoilCrashReproTheme

private data class Config(
    val crossfade: Boolean = false,
    val cropScale: Boolean = false,
    val directPlaceholder: Boolean = false,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoilCrashReproTheme {
                var config by remember { mutableStateOf(Config()) }

                Screen(
                    config = config,
                    onConfigChange = { config = it },
                )
            }
        }
    }
}

@Composable
private fun Screen(
    config: Config,
    onConfigChange: (Config) -> Unit,
) {
    var showImage by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(Modifier.padding(innerPadding).fillMaxWidth()) {
            LabeledSwitch(
                label = "Crossfade",
                checked = config.crossfade,
            ) {
                showImage = false
                onConfigChange(config.copy(crossfade = it))
            }

            LabeledSwitch(
                label = "ContentScale.Crop",
                checked = config.cropScale,
            ) {
                showImage = false
                onConfigChange(config.copy(cropScale = it))
            }

            LabeledSwitch(
                label = "Placeholder As Direct Parameter",
                checked = config.directPlaceholder,
            ) {
                showImage = false
                onConfigChange(config.copy(directPlaceholder = it))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showImage) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://raw.githubusercontent.com/cheonjaeung/repro-coil-crossfade-placeholder-crash/refs/heads/main/img_repro_1000x14000.png")
                        .crossfade(config.crossfade)
                        .apply { if (!config.directPlaceholder) placeholder(R.drawable.ic_placeholder) }
                        .build(),
                    contentDescription = null,
                    contentScale = if (config.cropScale) ContentScale.Crop else ContentScale.Fit,
                    placeholder = if (config.directPlaceholder) painterResource(R.drawable.ic_placeholder) else null,
                )
            } else {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { showImage = true },
                    ) {
                        Text(text = "Show Image")
                    }
                }
            }
        }
    }
}

@Composable
private fun LabeledSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
