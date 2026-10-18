package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AudioDevice
import com.example.model.DeviceType
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
fun AudioOutputSheet(
    devices: List<AudioDevice>,
    activeDevice: AudioDevice,
    onSelectDevice: (String) -> Unit,
    onToggleAnc: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var volume by remember { mutableFloatStateOf(0.75f) }

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
            // Drag handle pill
            Box(
                modifier = Modifier
                    .size(width = 38.dp, height = 4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3E4A62))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Saída de Mídia Galaxy",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Ecossistema Samsung Conectado",
                        style = MaterialTheme.typography.bodySmall.copy(color = GalaxyElectricBlue)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(OneUiSurfaceVariant)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.galaxy_buds_eco_1792176201030),
                        contentDescription = "Galaxy Buds",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Volume Control Slider
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
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Volume",
                            tint = GalaxyElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Volume de Mídia",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OneUiTextPrimary
                            )
                        )
                    }

                    Text(
                        text = "${(volume * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GalaxyElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Slider(
                    value = volume,
                    onValueChange = { volume = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = GalaxyElectricBlue,
                        inactiveTrackColor = Color(0xFF283246)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DISPOSITIVOS DISPONÍVEIS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OneUiTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Device list
            devices.forEach { device ->
                val isSelected = device.id == activeDevice.id
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSelected) GalaxyElectricBlue.copy(alpha = 0.12f) else OneUiSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) GalaxyElectricBlue else OneUiCardBorder,
                            RoundedCornerShape(18.dp)
                        )
                        .clickable { onSelectDevice(device.id) }
                        .padding(14.dp)
                        .testTag("device_item_${device.id}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) GalaxyElectricBlue else Color(0xFF232D3F)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getDeviceIcon(device.type),
                                    contentDescription = device.name,
                                    tint = if (isSelected) Color.White else OneUiTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = device.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = OneUiTextPrimary,
                                        fontSize = 15.sp
                                    )
                                )
                                if (device.batteryPercent != null) {
                                    Text(
                                        text = "Bateria: ${device.batteryPercent}% • Conexão Galaxy",
                                        style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                                    )
                                } else {
                                    Text(
                                        text = "Conectado via ecossistema Samsung",
                                        style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                                    )
                                }
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selecionado",
                                tint = GalaxyElectricBlue,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // ANC (Active Noise Cancelling) Switch if device supports it
                    if (device.hasNoiseCancelling && isSelected) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFF28344A))
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Cancelamento Ativo de Ruído (ANC)",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = OneUiTextPrimary
                                    )
                                )
                                Text(
                                    text = if (device.isAncActive) "Bloqueando ruído ambiente" else "Desativado",
                                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                                )
                            }

                            Switch(
                                checked = device.isAncActive,
                                onCheckedChange = { onToggleAnc(device.id) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = GalaxyElectricBlue,
                                    uncheckedThumbColor = OneUiTextMuted,
                                    uncheckedTrackColor = Color(0xFF28344A)
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private fun getDeviceIcon(type: DeviceType): ImageVector {
    return when (type) {
        DeviceType.GALAXY_BUDS -> Icons.Default.Headphones
        DeviceType.GALAXY_WATCH -> Icons.Default.Watch
        DeviceType.SAMSUNG_TV -> Icons.Default.Tv
        DeviceType.PHONE_SPEAKER -> Icons.Default.PhoneAndroid
    }
}
