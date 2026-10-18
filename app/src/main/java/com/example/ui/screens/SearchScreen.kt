package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SearchResults
import com.example.model.Track
import com.example.ui.components.OneUiHeader
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiSurfaceVariant
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.OneUiTextSecondary
import com.example.ui.theme.SpotifyGreen

@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: SearchResults,
    searchHistory: List<String>,
    onQueryChange: (String) -> Unit,
    onHistoryItemClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onAlbumClick: (String) -> Unit,
    onArtistClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Tudo") }
    val filters = listOf("Tudo", "Músicas", "Artistas", "Álbuns", "Playlists")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("search_screen_list"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // One UI Header
        item {
            OneUiHeader(
                title = "Buscar",
                subtitle = "Artistas, faixas, álbuns e podcasts"
            )
        }

        // One UI 9 Rounded Search Bar
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(
                            text = "O que você quer ouvir hoje?",
                            color = OneUiTextMuted
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = GalaxyElectricBlue
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpar",
                                    tint = OneUiTextSecondary
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GalaxyElectricBlue,
                        unfocusedBorderColor = OneUiCardBorder,
                        focusedContainerColor = OneUiSurfaceVariant,
                        unfocusedContainerColor = OneUiSurfaceVariant,
                        focusedTextColor = OneUiTextPrimary,
                        unfocusedTextColor = OneUiTextPrimary
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_text_input")
                )
            }
        }

        // Filter Pills
        item {
            Spacer(modifier = Modifier.height(14.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = filter == selectedFilter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) GalaxyElectricBlue else OneUiSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) GalaxyElectricBlue else OneUiCardBorder,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isSelected) Color.Black else OneUiTextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }

        // If Search Query is NOT empty, show results
        if (searchQuery.isNotBlank()) {
            val showAll = selectedFilter == "Tudo"

            // Spotify Iconic "Melhor resultado" (Top Result) Card
            if (showAll && (searchResults.artists.isNotEmpty() || searchResults.tracks.isNotEmpty())) {
                item {
                    val topArtist = searchResults.artists.firstOrNull()
                    val topTrack = searchResults.tracks.firstOrNull()

                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "Melhor resultado",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OneUiTextPrimary,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (topArtist != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(OneUiSurfaceVariant)
                                .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                                .clickable { onArtistClick(topArtist.id) }
                                .padding(16.dp)
                                .testTag("search_top_result_artist")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF22283A))
                            ) {
                                if (topArtist.avatarUrl.isNotEmpty()) {
                                    AsyncImage(
                                        model = topArtist.avatarUrl,
                                        contentDescription = topArtist.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = topArtist.name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OneUiTextPrimary,
                                    fontSize = 22.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "ARTISTA",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SpotifyGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    } else if (topTrack != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(OneUiSurfaceVariant)
                                .border(1.dp, OneUiCardBorder, RoundedCornerShape(20.dp))
                                .clickable { onTrackClick(topTrack) }
                                .padding(16.dp)
                                .testTag("search_top_result_track")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF22283A))
                            ) {
                                if (topTrack.coverUrl.isNotEmpty()) {
                                    AsyncImage(
                                        model = topTrack.coverUrl,
                                        contentDescription = topTrack.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = topTrack.title,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OneUiTextPrimary,
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${topTrack.artist} • MÚSICA",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SpotifyGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Tracks
            if (showAll || selectedFilter == "Músicas") {
                if (searchResults.tracks.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "MÚSICAS ENCONTRADAS (${searchResults.tracks.size})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(searchResults.tracks) { track ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(OneUiSurfaceVariant)
                                .clickable { onTrackClick(track) }
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
                                    text = "${track.artist} • ${track.album}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Artists
            if (showAll || selectedFilter == "Artistas") {
                if (searchResults.artists.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "ARTISTAS (${searchResults.artists.size})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(searchResults.artists) { artist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(OneUiSurfaceVariant)
                                .clickable { onArtistClick(artist.id) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF22283A))
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
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = artist.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = OneUiTextPrimary,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = artist.monthlyListeners,
                                    style = MaterialTheme.typography.bodySmall.copy(color = GalaxyElectricBlue)
                                )
                            }
                        }
                    }
                }
            }

            // Albums
            if (showAll || selectedFilter == "Álbuns") {
                if (searchResults.albums.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "ÁLBUNS (${searchResults.albums.size})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(searchResults.albums) { album ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(OneUiSurfaceVariant)
                                .clickable { onAlbumClick(album.id) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF22283A))
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
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = album.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = OneUiTextPrimary,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = "Álbum • ${album.artist} • ${album.releaseYear}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextMuted)
                                )
                            }
                        }
                    }
                }
            }

            // No results fallback
            if (searchResults.tracks.isEmpty() && searchResults.artists.isEmpty() && searchResults.albums.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(48.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nenhum resultado para \"$searchQuery\"",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = OneUiTextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Verifique a ortografia ou tente pesquisar outro termo.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = OneUiTextMuted)
                        )
                    }
                }
            }

        } else {
            // When query is EMPTY, show Search History & Categories
            if (searchHistory.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "BUSCAS RECENTES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OneUiTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "Limpar",
                            style = MaterialTheme.typography.bodySmall.copy(color = GalaxyElectricBlue),
                            modifier = Modifier.clickable { onClearHistory() }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(searchHistory) { historyItem ->
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(OneUiSurfaceVariant)
                                    .border(1.dp, OneUiCardBorder, RoundedCornerShape(16.dp))
                                    .clickable { onHistoryItemClick(historyItem) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = OneUiTextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = historyItem,
                                    style = MaterialTheme.typography.bodySmall.copy(color = OneUiTextPrimary)
                                )
                            }
                        }
                    }
                }
            }

            // Categories Section
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Navegar por Todas as Seções",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OneUiTextPrimary,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                val categories = listOf(
                    CategoryItem("Pop", listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))),
                    CategoryItem("Rock", listOf(Color(0xFFEF4444), Color(0xFF7F1D1D))),
                    CategoryItem("Eletrônica & Galaxy", listOf(Color(0xFF06B6D4), Color(0xFF2563EB))),
                    CategoryItem("Hip-Hop", listOf(Color(0xFFF59E0B), Color(0xFFD97706))),
                    CategoryItem("Sertanejo & Brasil", listOf(Color(0xFF10B981), Color(0xFF047857))),
                    CategoryItem("Lo-Fi & Foco", listOf(Color(0xFF6366F1), Color(0xFF4338CA)))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.chunked(2).forEach { rowCats ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowCats.forEach { cat ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(84.dp)
                                        .clip(RoundedCornerShape(18.dp))
                                        .background(Brush.linearGradient(cat.gradient))
                                        .clickable { onQueryChange(cat.title.split(" ")[0]) }
                                        .padding(14.dp)
                                ) {
                                    Text(
                                        text = cat.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class CategoryItem(val title: String, val gradient: List<Color>)
