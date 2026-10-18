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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Album
import com.example.model.Artist
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
fun ArtistDetailScreen(
    artist: Artist?,
    tracks: List<Track>,
    albums: List<Album>,
    onBackClick: () -> Unit,
    onTrackClick: (Track, List<Track>) -> Unit,
    onAlbumClick: (String) -> Unit,
    onFollowClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBackClick() }

    if (artist == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Artista não encontrado", color = OneUiTextMuted)
        }
        return
    }

    val artistTracks = tracks.filter { it.artistId == artist.id || artist.topTrackIds.contains(it.id) }
    val artistAlbums = albums.filter { it.artistId == artist.id || artist.albumIds.contains(it.id) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("artist_detail_screen"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // One UI Header with back button
        item {
            OneUiHeader(
                title = artist.name,
                subtitle = "Artista Oficial • ${artist.monthlyListeners}",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }

        // Artist Avatar & Follow Button
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(16.dp, CircleShape, spotColor = GalaxyElectricBlue)
                        .clip(CircleShape)
                        .background(Color(0xFF22283A))
                        .border(3.dp, GalaxyElectricBlue, CircleShape)
                ) {
                    if (artist.avatarUrl.isNotEmpty()) {
                        AsyncImage(
                            model = artist.avatarUrl,
                            contentDescription = artist.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = artist.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verificado",
                        tint = GalaxyElectricBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = artist.monthlyListeners,
                    style = MaterialTheme.typography.bodyMedium.copy(color = GalaxyElectricBlue)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (artistTracks.isNotEmpty()) onTrackClick(artistTracks[0], artistTracks)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SpotifyGreen,
                            contentColor = OneUiPureBlack
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ouvir Músicas", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { onFollowClick(artist.id) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        if (artist.isFollowed) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = GalaxyElectricBlue)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Seguindo", color = GalaxyElectricBlue, fontWeight = FontWeight.SemiBold)
                        } else {
                            Text("Seguir", color = OneUiTextPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Section: Mais Tocadas
        item {
            Text(
                text = "POPULARES",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OneUiTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
            )
        }

        items(artistTracks) { track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OneUiSurfaceVariant)
                    .clickable { onTrackClick(track, artistTracks) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF22283A))
                ) {
                    if (track.coverUrl.isNotEmpty()) {
                        AsyncImage(
                            model = track.coverUrl,
                            contentDescription = track.album,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

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
                        text = "${track.album} • ${track.playsCount} streams",
                        style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                    )
                }
            }
        }

        // Section: Discografia / Álbuns
        if (artistAlbums.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "DISCOGRAFIA",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OneUiTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(artistAlbums) { album ->
                        Column(
                            modifier = Modifier
                                .width(130.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable { onAlbumClick(album.id) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color(0xFF22283A))
                                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(18.dp))
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
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = album.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OneUiTextPrimary
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${album.releaseYear} • ${album.genre}",
                                style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // Bio Card
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(OneUiSurfaceVariant)
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Text(
                    text = "Sobre o Artista",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artist.bio,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = OneUiTextSecondary,
                        lineHeight = 22.sp
                    )
                )
            }
        }
    }
}
