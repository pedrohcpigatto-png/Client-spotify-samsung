package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.SpatialAudio
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SoundAlivePreset
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiDarkBackground
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqualizerSheet(
    activePreset: SoundAlivePreset,
    dolbyAtmos: Boolean,
    spatialAudio: Boolean,
    onSelectPreset: (SoundAlivePreset) -> Unit,
    onToggleDolbyAtmos: () -> Unit,
    onToggleSpatialAudio: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = OneUiDarkBackground,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .size(width = 38.dp, height = 4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3E4A62))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Equalizer,
                    contentDescription = null,
                    tint = GalaxyElectricBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Galaxy SoundAlive™",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 20.sp
                    )
                )
            }

            Text(
                text = "Processamento de Áudio One UI 9 & Dolby Atmos",
                style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Dolby Atmos Switch Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SurroundSound,
                            contentDescription = null,
                            tint = if (dolbyAtmos) GalaxyElectricBlue else OneUiTextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Dolby Atmos® para Música",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OneUiTextPrimary,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = "Áudio tridimensional calibrado para Galaxy",
                                style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                            )
                        }
                    }

                    Switch(
                        checked = dolbyAtmos,
                        onCheckedChange = { onToggleDolbyAtmos() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = GalaxyElectricBlue
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF28344A))
                )
                Spacer(modifier = Modifier.height(14.dp))

                // 360 Spatial Audio Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SpatialAudio,
                            contentDescription = null,
                            tint = if (spatialAudio) SpotifyGreen else OneUiTextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Áudio Espacial 360 com Galaxy Buds",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OneUiTextPrimary,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = "Rastreamento dinâmico da cabeça",
                                style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                            )
                        }
                    }

                    Switch(
                        checked = spatialAudio,
                        onCheckedChange = { onToggleSpatialAudio() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SpotifyGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "PRESETS SOUNDALIVE",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OneUiTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Presets List
            SoundAlivePreset.entries.forEach { preset ->
                val isSelected = preset == activePreset
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) GalaxyElectricBlue.copy(alpha = 0.15f) else OneUiSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) GalaxyElectricBlue else OneUiCardBorder,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { onSelectPreset(preset) }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .testTag("preset_${preset.name}"),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = preset.label,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) GalaxyElectricBlue else OneUiTextPrimary
                            )
                        )
                        Text(
                            text = preset.description,
                            style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                        )
                    }

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = GalaxyElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
