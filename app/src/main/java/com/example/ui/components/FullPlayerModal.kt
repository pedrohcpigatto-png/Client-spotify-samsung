package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AudioDevice
import com.example.model.SoundAlivePreset
import com.example.model.Track
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiDarkBackground
import com.example.ui.theme.OneUiGlassBorder
import com.example.ui.theme.OneUiPureBlack
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen
import com.example.ui.theme.SpotifyGreenBright

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPlayerModal(
    track: Track?,
    isPlaying: Boolean,
    currentPositionMs: Long,
    durationMs: Long,
    isShuffle: Boolean,
    isRepeat: Boolean,
    activeDevice: AudioDevice,
    activePreset: SoundAlivePreset,
    dolbyAtmos: Boolean,
    onDismiss: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSeek: (Long) -> Unit,
    onShuffleToggle: () -> Unit,
    onRepeatToggle: () -> Unit,
    onLikeToggle: (String) -> Unit,
    onDeviceOutputClick: () -> Unit,
    onEqualizerClick: () -> Unit,
    onQuickShareClick: () -> Unit,
    onAddToPlaylistClick: (Track) -> Unit,
    onDownloadToggle: () -> Unit = {}
) {
    if (track == null) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showLyrics by remember { mutableStateOf(false) }

    var isUserScrubbing by remember { mutableStateOf(false) }
    var scrubPosition by remember { mutableFloatStateOf(0f) }

    val currentScrubTime = if (isUserScrubbing) scrubPosition.toLong() else currentPositionMs

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = OneUiDarkBackground,
        dragHandle = null,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("full_player_collapse_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Recolher Player",
                        tint = OneUiTextPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "TOCANDO DO SPOTIFY GALAXY",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = SpotifyGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = track.album,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = OneUiTextSecondary,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row {
                    IconButton(
                        onClick = onQuickShareClick,
                        modifier = Modifier.testTag("full_player_quick_share")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Quick Share",
                            tint = OneUiTextPrimary
                        )
                    }
                    IconButton(
                        onClick = { onAddToPlaylistClick(track) },
                        modifier = Modifier.testTag("full_player_menu_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Adicionar à Playlist",
                            tint = OneUiTextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!showLyrics) {
                // Large Album Art View
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f)
                        .shadow(
                            elevation = 30.dp,
                            shape = RoundedCornerShape(32.dp),
                            ambientColor = Color.Black,
                            spotColor = GalaxyElectricBlue.copy(alpha = 0.5f)
                        )
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color(0xFF1E2638))
                        .border(1.5.dp, OneUiGlassBorder, RoundedCornerShape(32.dp))
                        .testTag("full_player_album_art")
                ) {
                    if (track.coverUrl.isNotEmpty()) {
                        AsyncImage(
                            model = track.coverUrl,
                            contentDescription = track.album,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        listOf(GalaxyElectricBlue, SpotifyGreen)
                                    )
                                )
                        )
                    }
                }
            } else {
                // Synchronized Lyrics View
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(OneUiSurfaceVariant.copy(alpha = 0.5f))
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(24.dp))
                        .padding(16.dp)
                ) {
                    SynchronizedLyricsViewer(
                        lyrics = track.lyrics,
                        currentPositionMs = currentPositionMs,
                        onSeekTo = onSeek
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Track Title & Artist & Like Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary,
                            fontSize = 22.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = track.artist,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = OneUiTextSecondary,
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onDownloadToggle,
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("full_player_download_button")
                    ) {
                        if (track.isDownloading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp
                            )
                        } else if (track.isDownloaded) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Baixado Offline",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(26.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.ArrowCircleDown,
                                contentDescription = "Baixar para ouvir offline",
                                tint = OneUiTextSecondary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = { onLikeToggle(track.id) },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("full_player_like_button")
                    ) {
                        Icon(
                            imageVector = if (track.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (track.isLiked) "Descurtir" else "Curtir",
                            tint = if (track.isLiked) MaterialTheme.colorScheme.primary else OneUiTextSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scrubber Slider
            val maxDuration = durationMs.coerceAtLeast(1L).toFloat()
            Slider(
                value = currentScrubTime.toFloat().coerceIn(0f, maxDuration),
                onValueChange = {
                    isUserScrubbing = true
                    scrubPosition = it
                },
                onValueChangeFinished = {
                    onSeek(scrubPosition.toLong())
                    isUserScrubbing = false
                },
                valueRange = 0f..maxDuration,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = Color(0xFF283246)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("player_scrubber_slider")
            )

            // Scrubber Timestamps
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatMs(currentScrubTime),
                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                )
                Text(
                    text = formatMs(durationMs),
                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shuffle
                IconButton(
                    onClick = onShuffleToggle,
                    modifier = Modifier.testTag("player_shuffle_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Aleatório",
                        tint = if (isShuffle) MaterialTheme.colorScheme.primary else OneUiTextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Previous
                IconButton(
                    onClick = onPreviousClick,
                    modifier = Modifier.testTag("player_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Anterior",
                        tint = OneUiTextPrimary,
                        modifier = Modifier.size(34.dp)
                    )
                }

                // Rewind 10s
                IconButton(
                    onClick = { onSeek((currentPositionMs - 10000L).coerceAtLeast(0L)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay10,
                        contentDescription = "Voltar 10s",
                        tint = OneUiTextSecondary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Play / Pause Circle
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = 0.85f))
                            )
                        )
                        .shadow(12.dp, CircleShape, ambientColor = MaterialTheme.colorScheme.primary, spotColor = MaterialTheme.colorScheme.primary)
                        .clickable(onClick = onPlayPauseClick)
                        .testTag("player_play_pause_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pausar" else "Reproduzir",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(38.dp)
                    )
                }

                // Forward 10s
                IconButton(
                    onClick = { onSeek((currentPositionMs + 10000L).coerceAtMost(durationMs)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Forward10,
                        contentDescription = "Avançar 10s",
                        tint = OneUiTextSecondary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Next
                IconButton(
                    onClick = onNextClick,
                    modifier = Modifier.testTag("player_next_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Próxima",
                        tint = OneUiTextPrimary,
                        modifier = Modifier.size(34.dp)
                    )
                }

                // Repeat
                IconButton(
                    onClick = onRepeatToggle,
                    modifier = Modifier.testTag("player_repeat_button")
                ) {
                    Icon(
                        imageVector = if (isRepeat) Icons.Default.RepeatOne else Icons.Default.Repeat,
                        contentDescription = "Repetir",
                        tint = if (isRepeat) SpotifyGreen else OneUiTextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Galaxy Ecosystem Quick Badges (Bottom Area)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Device Output Switcher
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(OneUiSurfaceVariant)
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                        .clickable(onClick = onDeviceOutputClick)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("player_output_switcher")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Headphones,
                            contentDescription = "Saída de Áudio",
                            tint = GalaxyElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${activeDevice.name} ${if (activeDevice.batteryPercent != null) "(${activeDevice.batteryPercent}%)" else ""}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                // SoundAlive & Dolby Atmos Preset
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(OneUiSurfaceVariant)
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                        .clickable(onClick = onEqualizerClick)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("player_soundalive_preset")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Equalizer,
                            contentDescription = "SoundAlive",
                            tint = if (dolbyAtmos) GalaxyElectricBlue else OneUiTextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (dolbyAtmos) "Dolby Atmos • ${activePreset.label}" else activePreset.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
            if (!showLyrics && track.lyrics.isNotEmpty()) {
                val currentLine = track.lyrics.lastOrNull { it.timeMs <= currentPositionMs }?.text
                    ?: track.lyrics.firstOrNull()?.text ?: ""

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFF2C1E26))
                        .border(1.dp, OneUiCardBorder, RoundedCornerShape(22.dp))
                        .clickable { showLyrics = true }
                        .padding(16.dp)
                        .testTag("player_lyrics_preview_card")
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "LETRAS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "MAIS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentLine,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                lineHeight = 22.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SynchronizedLyricsViewer(
    lyrics: List<com.example.model.LyricsLine>,
    currentPositionMs: Long,
    onSeekTo: (Long) -> Unit
) {
    if (lyrics.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Letra sincronizada não disponível para esta faixa.",
                style = MaterialTheme.typography.bodyMedium.copy(color = OneUiTextMuted),
                textAlign = TextAlign.Center
            )
        }
        return
    }

    val listState = rememberLazyListState()

    // Find current active index
    val activeIndex = lyrics.indexOfLast { it.timeMs <= currentPositionMs }.coerceAtLeast(0)

    LaunchedEffect(activeIndex) {
        if (activeIndex >= 0 && activeIndex < lyrics.size) {
            listState.animateScrollToItem((activeIndex - 1).coerceAtLeast(0))
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "LETRAS SINCRONIZADAS ONE UI",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SpotifyGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }

        itemsIndexed(lyrics) { index, line ->
            val isActive = index == activeIndex
            val scale by animateFloatAsState(targetValue = if (isActive) 1.05f else 1f, label = "textScale")

            Text(
                text = line.text,
                style = if (isActive) {
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SpotifyGreenBright,
                        fontSize = 20.sp
                    )
                } else {
                    MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = OneUiTextMuted,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSeekTo(line.timeMs) }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

private fun formatMs(ms: Long): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
