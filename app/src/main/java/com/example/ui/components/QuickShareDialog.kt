package com.example.ui.components

import android.widget.Toast
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TabletAndroid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Track
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
fun QuickShareDialog(
    track: Track?,
    onDismiss: () -> Unit
) {
    if (track == null) return
    val context = LocalContext.current
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
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = GalaxyElectricBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Samsung Quick Share",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Compartilhe \"${track.title}\" com dispositivos Galaxy próximos",
                        style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DISPOSITIVOS GALAXY PRÓXIMOS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OneUiTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            val devicesNearby = listOf(
                Triple("Galaxy Tab S9 Ultra", "Tablet Galaxy", Icons.Default.TabletAndroid),
                Triple("Galaxy Book4 Pro 360", "Notebook Windows", Icons.Default.Laptop),
                Triple("Galaxy S25 Ultra de Pedro", "Smartphone", Icons.Default.PhoneAndroid)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                devicesNearby.forEach { (name, type, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(OneUiSurfaceVariant)
                            .border(1.dp, OneUiCardBorder, RoundedCornerShape(16.dp))
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "Enviando \"${track.title}\" para $name via Quick Share...",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onDismiss()
                            }
                            .padding(vertical = 14.dp, horizontal = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(GalaxyElectricBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = name,
                                tint = GalaxyElectricBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OneUiTextPrimary,
                                fontSize = 11.sp
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = type,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextMuted,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons (Copy Link & QR Code)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OneUiSurfaceVariant)
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(16.dp))
                        .clickable {
                            Toast.makeText(
                                context,
                                "Link do Spotify copiado para a área de transferência!",
                                Toast.LENGTH_SHORT
                            ).show()
                            onDismiss()
                        }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar Link",
                            tint = SpotifyGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Copiar Link",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = OneUiTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OneUiSurfaceVariant)
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(16.dp))
                        .clickable {
                            Toast.makeText(
                                context,
                                "QR Code Quick Share gerado!",
                                Toast.LENGTH_SHORT
                            ).show()
                            onDismiss()
                        }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = "QR Code",
                            tint = GalaxyElectricBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Código QR",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = OneUiTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
