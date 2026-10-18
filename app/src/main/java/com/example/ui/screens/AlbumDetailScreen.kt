package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Album
import com.example.model.Track
import com.example.ui.components.OneUiHeader
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiPureBlack
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen

@Composable
fun AlbumDetailScreen(
    album: Album?,
    tracks: List<Track>,
    onBackClick: () -> Unit,
    onTrackClick: (Track, List<Track>) -> Unit,
    onPlayAllClick: (List<Track>) -> Unit,
    onShuffleClick: (List<Track>) -> Unit,
    onLikeTrack: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBackClick() }

    if (album == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Álbum não encontrado", color = OneUiTextMuted)
        }
        return
    }

    val albumTracks = tracks.filter { it.albumId == album.id || album.trackIds.contains(it.id) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("album_detail_screen"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // One UI Header with back button
        item {
            OneUiHeader(
                title = album.title,
                subtitle = "Álbum • ${album.artist} • ${album.releaseYear}",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }

        // Album Art
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 10.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFF22283A))
                    .border(1.2.dp, OneUiCardBorder, RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (album.coverUrl.isNotEmpty()) {
                    AsyncImage(
                        model = album.coverUrl,
                        contentDescription = album.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Metadata Info
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${album.artist} • Lançado em ${album.releaseYear} • ${album.recordLabel}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = OneUiTextSecondary)
                )
                Text(
                    text = "Gênero: ${album.genre} • Áudio Masterizado Galaxy",
                    style = MaterialTheme.typography.bodySmall.copy(color = GalaxyElectricBlue)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Play / Shuffle Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onPlayAllClick(albumTracks) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SpotifyGreen,
                            contentColor = OneUiPureBlack
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Reproduzir", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { onShuffleClick(albumTracks) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Shuffle, contentDescription = null, tint = OneUiTextPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Aleatório", color = OneUiTextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "FAIXAS DO ÁLBUM (${albumTracks.size})",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OneUiTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Tracklist
        itemsIndexed(albumTracks) { index, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OneUiSurfaceVariant)
                    .clickable { onTrackClick(track, albumTracks) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${index + 1}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = OneUiTextMuted,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.width(28.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OneUiTextPrimary,
                            fontSize = 15.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = track.artist,
                        style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                    )
                }

                IconButton(onClick = { onLikeTrack(track.id) }) {
                    Icon(
                        imageVector = if (track.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = if (track.isLiked) SpotifyGreen else OneUiTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
