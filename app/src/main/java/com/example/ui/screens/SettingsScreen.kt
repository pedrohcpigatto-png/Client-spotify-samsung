package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.OneUiHeader
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiDarkBackground
import com.example.ui.theme.OneUiPureBlack
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import com.example.model.SamsungPalette

@Composable
fun SettingsScreen(
    isConnectedToOfficialApi: Boolean,
    batterySaverMode: Boolean,
    amoledPureBlack: Boolean,
    samsungPalette: SamsungPalette,
    onBackClick: () -> Unit,
    onSaveSpotifyToken: (String) -> Unit,
    onToggleBatterySaver: (Boolean) -> Unit,
    onToggleAmoledBlack: (Boolean) -> Unit,
    onSelectPalette: (SamsungPalette) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBackClick() }
    val context = LocalContext.current
    var tokenInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen_list"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // One UI Header
        item {
            OneUiHeader(
                title = "Configurações",
                subtitle = "Galaxy Music • Integração Spotify & Samsung",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }

        // Section 0: Paleta de Cores One UI (Material You)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Paleta de Cores (Material You)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OneUiTextPrimary
                            )
                        )
                        Text(
                            text = "Estilo de Cores da Samsung One UI",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Escolha como os realces, botões de reprodução, Now Bar e cartões se adaptam às cores do seu aparelho:",
                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextSecondary)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SamsungPalette.entries.forEach { paletteOption ->
                        val isSelected = paletteOption == samsungPalette
                        val color = Color(android.graphics.Color.parseColor(paletteOption.primaryHex))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) color.copy(alpha = 0.15f) else Color(0xFF161C28))
                                .border(
                                    1.dp,
                                    if (isSelected) color else OneUiCardBorder,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    onSelectPalette(paletteOption)
                                    Toast.makeText(
                                        context,
                                        "Paleta \"${paletteOption.label}\" aplicada!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = paletteOption.label,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) OneUiTextPrimary else OneUiTextSecondary
                                    )
                                )
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selecionado",
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 1: API Oficial do Spotify
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SpotifyGreen.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = SpotifyGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "API Oficial do Spotify",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OneUiTextPrimary
                            )
                        )
                        Text(
                            text = if (isConnectedToOfficialApi) "Conectado via Bearer Token" else "Modo Nativo Galaxy Ativo",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isConnectedToOfficialApi) SpotifyGreen else OneUiTextMuted
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Insira um token de acesso OAuth do Spotify Web API para sincronizar sua conta e reproduzir via Spotify Connect:",
                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextSecondary)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = tokenInput,
                    onValueChange = { tokenInput = it },
                    placeholder = { Text("Cole o Bearer Token do Spotify...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = SpotifyGreen)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpotifyGreen,
                        unfocusedBorderColor = OneUiCardBorder,
                        focusedTextColor = OneUiTextPrimary,
                        unfocusedTextColor = OneUiTextPrimary
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().testTag("spotify_token_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (tokenInput.isNotBlank()) {
                            onSaveSpotifyToken(tokenInput)
                            Toast.makeText(context, "Token do Spotify configurado com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            onSaveSpotifyToken("")
                            Toast.makeText(context, "Alternado para Catálogo Nativo Galaxy", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SpotifyGreen,
                        contentColor = OneUiPureBlack
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp).testTag("save_token_button")
                ) {
                    Text(
                        text = if (tokenInput.isNotBlank()) "Conectar à Conta Spotify" else "Usar Motor Offline Galaxy",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Section 2: Otimização de Bateria Galaxy
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(GalaxyElectricBlue.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.BatterySaver,
                            contentDescription = null,
                            tint = GalaxyElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Otimização de Bateria Galaxy",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OneUiTextPrimary
                            )
                        )
                        Text(
                            text = "Ajustado especificamente para processadores Exynos e Snapdragon Galaxy",
                            style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Switch 1: Economia de Bateria One UI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Modo Alta Eficiência Energética",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OneUiTextPrimary
                            )
                        )
                        Text(
                            text = "Desativa animações pesadas do visualizador e otimiza o buffer de reprodução de áudio",
                            style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = batterySaverMode,
                        onCheckedChange = onToggleBatterySaver,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = GalaxyElectricBlue
                        ),
                        modifier = Modifier.testTag("battery_saver_switch")
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(OneUiCardBorder))
                Spacer(modifier = Modifier.height(14.dp))

                // Switch 2: Preto Puro AMOLED
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Preto Puro Dynamic AMOLED 2X",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OneUiTextPrimary
                            )
                        )
                        Text(
                            text = "Desliga pixels pretos nas telas Galaxy para máxima economia e contraste infinito",
                            style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = amoledPureBlack,
                        onCheckedChange = onToggleAmoledBlack,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = GalaxyElectricBlue
                        ),
                        modifier = Modifier.testTag("amoled_black_switch")
                    )
                }
            }
        }

        // Section 3: Recursos Samsung One UI 9
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Text(
                    text = "RECURSOS NATIVOS ONE UI 9",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GalaxyElectricBlue,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                val features = listOf(
                    "Dynamic Now Bar nativa com animação fluida de ondas sonoras",
                    "Samsung SoundAlive™ & Processamento Dolby Atmos® integrado",
                    "Troca de saída de áudio com suporte a Galaxy Buds3 Pro e 360 Audio",
                    "Compartilhamento instantâneo via Samsung Quick Share",
                    "Letras sincronizadas em tempo real estilo Karaokê One UI"
                )

                features.forEach { feat ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SpotifyGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = feat,
                            style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextSecondary)
                        )
                    }
                }
            }
        }

        // Section 4: Sobre
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = OneUiTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sobre o Galaxy Music",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Aplicativo de música nativo desenvolvido com a identidade visual One UI 9 da Samsung e arquitetura Jetpack Compose moderna com aceleração gráfica para linha Galaxy.",
                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted, lineHeight = 20.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Versão One UI: 9.0 • Build Samsung Galaxy 2026",
                    style = MaterialTheme.typography.labelSmall.copy(color = GalaxyElectricBlue)
                )
            }
        }
    }
}
