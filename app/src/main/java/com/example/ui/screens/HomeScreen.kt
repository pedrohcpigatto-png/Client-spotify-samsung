package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.model.Album
import com.example.model.Artist
import com.example.model.Playlist
import com.example.model.Track
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen
import com.example.ui.theme.SpotifyGreenBright

@Composable
fun HomeScreen(
    tracks: List<Track>,
    albums: List<Album>,
    artists: List<Artist>,
    playlists: List<Playlist>,
    activeDeviceName: String,
    onTrackClick: (Track) -> Unit,
    onAlbumClick: (String) -> Unit,
    onArtistClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onLikeTrack: (String) -> Unit,
    onTrackMenuClick: (Track) -> Unit,
    onDeviceClick: () -> Unit,
    onEqualizerClick: () -> Unit,
    onShareClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Tudo") }
    val filterTabs = listOf("Tudo", "Música", "Podcasts")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // Spotify Top Bar with User Avatar, Filter Pills, and Samsung Galaxy Ecosystem Badges
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Avatar (Spotify style with One UI squircle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(GalaxyElectricBlue, SpotifyGreen)
                                    )
                                )
                                .border(1.5.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "G",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Filter Pills: "Tudo", "Música", "Podcasts"
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            filterTabs.forEach { tab ->
                                val isSelected = tab == selectedFilter
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if (isSelected) SpotifyGreen else OneUiSurfaceVariant)
                                        .border(
                                            1.dp,
                                            if (isSelected) SpotifyGreen else OneUiCardBorder,
                                            RoundedCornerShape(20.dp)
                                        )
                                        .clickable { selectedFilter = tab }
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = tab,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = if (isSelected) Color.Black else OneUiTextPrimary,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Ecosystem Quick Actions (Galaxy Buds badge, SoundAlive, Settings)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(GalaxyElectricBlue.copy(alpha = 0.15f))
                                .clickable { onDeviceClick() }
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = "Galaxy Buds",
                                    tint = GalaxyElectricBlue,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = activeDeviceName.split(" ").take(2).joinToString(" "),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GalaxyElectricBlue,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        IconButton(
                            onClick = onEqualizerClick,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Equalizer,
                                contentDescription = "Equalizador",
                                tint = OneUiTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = onSettingsClick,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Configurações",
                                tint = OneUiTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Spotify Greeting Heading with One UI 9 font weight
                Text(
                    text = "Boa tarde",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 26.sp
                    )
                )
            }
        }

        // The Signature Spotify 6-Card Quick-Play Grid with One UI 9 squircle styling
        item {
            val quickItems = listOf(
                QuickPlayItem("Músicas Curtidas", "#1DB954", true, "pl_liked"),
                QuickPlayItem("Galaxy Daily Mix", "#2B82F6", false, "pl_daily_galaxy"),
                QuickPlayItem("Top 50 Brasil", "#10B981", false, "pl_top_brasil"),
                QuickPlayItem("After Hours Neon", "#EF4444", false, "album_afterhours", isAlbum = true),
                QuickPlayItem("One UI Midnight", "#8B5CF6", false, "pl_chill_night"),
                QuickPlayItem("Starboy Deluxe", "#F59E0B", false, "album_starboy", isAlbum = true)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickItems.chunked(2).forEach { rowPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowPair.forEach { item ->
                            val color = Color(android.graphics.Color.parseColor(item.colorHex))
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(OneUiSurfaceVariant)
                                    .border(0.8.dp, OneUiCardBorder, RoundedCornerShape(14.dp))
                                    .clickable {
                                        if (item.isAlbum) onAlbumClick(item.targetId)
                                        else onPlaylistClick(item.targetId)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp))
                                        .background(
                                            Brush.linearGradient(
                                                listOf(color, color.copy(alpha = 0.6f))
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (item.isLiked) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OneUiTextPrimary,
                                        fontSize = 12.sp
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }

        // Section: Feito para você (Spotify style horizontal row with One UI 9 squircle cards)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Feito para você",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 18.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(playlists) { pl ->
                        val color = try {
                            Color(android.graphics.Color.parseColor(pl.coverColorHex))
                        } catch (_: Exception) {
                            GalaxyElectricBlue
                        }

                        Column(
                            modifier = Modifier
                                .width(152.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onPlaylistClick(pl.id) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(152.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(color, color.copy(alpha = 0.5f), Color(0xFF10141E))
                                        )
                                    )
                                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                                    .padding(14.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Text(
                                    text = pl.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = pl.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OneUiTextMuted,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        // Spotify Section: "Tocados recentemente" (Albums horizontal row)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tocados recentemente",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 18.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(albums) { album ->
                        Column(
                            modifier = Modifier
                                .width(148.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onAlbumClick(album.id) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(148.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFF22283A))
                                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
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

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = album.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OneUiTextPrimary,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = "Álbum • ${album.artist}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OneUiTextMuted,
                                    fontSize = 12.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        // Spotify Section: "Seus artistas favoritos" (Circular avatar cards)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Seus artistas favoritos",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 18.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(artists) { artist ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(110.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable { onArtistClick(artist.id) }
                                .padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(104.dp)
                                    .shadow(6.dp, CircleShape, spotColor = GalaxyElectricBlue)
                                    .clip(CircleShape)
                                    .background(Color(0xFF22283A))
                                    .border(2.dp, GalaxyElectricBlue.copy(alpha = 0.4f), CircleShape)
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

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = artist.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = OneUiTextPrimary,
                                        fontSize = 13.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verificado",
                                    tint = GalaxyElectricBlue,
                                    modifier = Modifier.size(12.dp)
                                )
                            }

                            Text(
                                text = "Artista",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OneUiTextMuted,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        // Samsung Galaxy Ecosystem Special Banner Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(24.dp))
                    .clickable { onEqualizerClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.galaxy_music_banner_1792176187570),
                    contentDescription = "Galaxy Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xEB090C10))
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DOLBY ATMOS & SOUNDALIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SpotifyGreenBright,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "Calibrado para Galaxy Buds3 Pro",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OneUiTextPrimary,
                                fontSize = 15.sp
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SpotifyGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Configurar",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        // Spotify Section: "Mais tocadas no Brasil" (Tracks list)
        item {
            Text(
                text = "Mais tocadas no Brasil",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = OneUiTextPrimary,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(tracks) { track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OneUiSurfaceVariant.copy(alpha = 0.6f))
                    .border(0.8.dp, OneUiCardBorder, RoundedCornerShape(16.dp))
                    .clickable { onTrackClick(track) }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
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
                        text = "${track.artist} • ${track.album}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OneUiTextMuted,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = { onLikeTrack(track.id) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (track.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = if (track.isLiked) SpotifyGreen else OneUiTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = { onTrackMenuClick(track) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opções",
                        tint = OneUiTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

data class QuickPlayItem(
    val title: String,
    val colorHex: String,
    val isLiked: Boolean,
    val targetId: String,
    val isAlbum: Boolean = false
)
