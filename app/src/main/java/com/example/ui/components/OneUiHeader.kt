package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen

@Composable
fun OneUiHeader(
    title: String,
    subtitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onDeviceClick: (() -> Unit)? = null,
    onEqualizerClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null,
    activeDeviceName: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Top Action Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("header_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = OneUiTextPrimary
                    )
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SpotifyGreen)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ONE UI 9 • SPOTIFY",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = SpotifyGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                    )
                }
            }

            // Quick actions
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (activeDeviceName != null && onDeviceClick != null) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(GalaxyElectricBlue.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.testTag("header_device_badge")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = "Dispositivo de Áudio",
                                tint = GalaxyElectricBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = activeDeviceName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GalaxyElectricBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                if (onEqualizerClick != null) {
                    IconButton(
                        onClick = onEqualizerClick,
                        modifier = Modifier.testTag("header_equalizer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Equalizer,
                            contentDescription = "Equalizador Galaxy",
                            tint = OneUiTextSecondary
                        )
                    }
                }

                if (onShareClick != null) {
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier.testTag("header_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Quick Share",
                            tint = OneUiTextSecondary
                        )
                    }
                }

                if (onSettingsClick != null) {
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier.testTag("header_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
                            tint = OneUiTextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Large One UI Title (Viewing Area)
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = OneUiTextPrimary,
                fontSize = 32.sp
            ),
            modifier = Modifier.testTag("header_title_text")
        )

        if (!subtitle.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OneUiTextMuted,
                    fontSize = 14.sp
                )
            )
        }
    }
}
