package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Track
import com.example.ui.components.AddToPlaylistDialog
import com.example.ui.components.AudioOutputSheet
import com.example.ui.components.CreatePlaylistDialog
import com.example.ui.components.EqualizerSheet
import com.example.ui.components.FullPlayerModal
import com.example.ui.components.NowBar
import com.example.ui.components.QuickShareDialog
import com.example.ui.screens.AlbumDetailScreen
import com.example.ui.screens.ArtistDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.PlaylistDetailScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.GalaxyElectricBlue
import com.example.ui.theme.GalaxyMusicTheme
import com.example.ui.theme.OneUiCardBorder
import com.example.ui.theme.OneUiDarkBackground
import com.example.ui.theme.OneUiPureBlack
import com.example.ui.theme.OneUiSurface
import com.example.ui.theme.OneUiTextMuted
import com.example.ui.theme.OneUiTextPrimary
import com.example.ui.theme.SpotifyGreen
import com.example.viewmodel.GalaxyMusicViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: GalaxyMusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val amoledPureBlack by viewModel.amoledPureBlack.collectAsStateWithLifecycle()
            val samsungPalette by viewModel.samsungPalette.collectAsStateWithLifecycle()

            GalaxyMusicTheme(palette = samsungPalette, pureBlackAmoled = amoledPureBlack) {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: GalaxyMusicViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()
    val albums by viewModel.albums.collectAsStateWithLifecycle()
    val artists by viewModel.artists.collectAsStateWithLifecycle()
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val activeDevice by viewModel.activeDevice.collectAsStateWithLifecycle()

    // Player state
    val isPlaying by viewModel.audioPlayer.isPlaying.collectAsStateWithLifecycle()
    val currentTrack by viewModel.audioPlayer.currentTrack.collectAsStateWithLifecycle()
    val currentPositionMs by viewModel.audioPlayer.currentPositionMs.collectAsStateWithLifecycle()
    val durationMs by viewModel.audioPlayer.durationMs.collectAsStateWithLifecycle()
    val isShuffle by viewModel.audioPlayer.isShuffle.collectAsStateWithLifecycle()
    val isRepeat by viewModel.audioPlayer.isRepeat.collectAsStateWithLifecycle()
    val activePreset by viewModel.audioPlayer.activePreset.collectAsStateWithLifecycle()
    val dolbyAtmos by viewModel.audioPlayer.dolbyAtmosEnabled.collectAsStateWithLifecycle()
    val spatialAudio by viewModel.audioPlayer.spatialAudio360.collectAsStateWithLifecycle()

    // Modals
    val isFullPlayerVisible by viewModel.isFullPlayerVisible.collectAsStateWithLifecycle()
    val isAudioOutputSheetVisible by viewModel.isAudioOutputSheetVisible.collectAsStateWithLifecycle()
    val isEqualizerSheetVisible by viewModel.isEqualizerSheetVisible.collectAsStateWithLifecycle()
    val isQuickShareVisible by viewModel.isQuickShareVisible.collectAsStateWithLifecycle()
    val isCreatePlaylistVisible by viewModel.isCreatePlaylistVisible.collectAsStateWithLifecycle()
    val trackToAddToPlaylist by viewModel.trackToAddToPlaylist.collectAsStateWithLifecycle()

    // Search & Settings
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val isConnectedToSpotifyApi by viewModel.isConnectedToSpotifyApi.collectAsStateWithLifecycle()
    val batterySaverMode by viewModel.batterySaverMode.collectAsStateWithLifecycle()
    val amoledPureBlack by viewModel.amoledPureBlack.collectAsStateWithLifecycle()
    val samsungPalette by viewModel.samsungPalette.collectAsStateWithLifecycle()

    // Back handling for screens
    BackHandler(enabled = currentScreen !is Screen.Home) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // Samsung One UI 9 Native Now Bar
                if (currentTrack != null) {
                    NowBar(
                        track = currentTrack,
                        isPlaying = isPlaying,
                        currentPositionMs = currentPositionMs,
                        durationMs = durationMs,
                        deviceName = activeDevice.name,
                        onBarClick = { viewModel.setFullPlayerVisible(true) },
                        onPlayPauseClick = { viewModel.togglePlayPause() },
                        onNextClick = { viewModel.nextTrack() },
                        batterySaver = batterySaverMode
                    )
                }

                // One UI 9 Bottom Navigation Bar
                OneUiBottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        tracks = tracks,
                        albums = albums,
                        artists = artists,
                        playlists = playlists,
                        activeDeviceName = activeDevice.name,
                        onTrackClick = { track -> viewModel.playTrack(track, tracks) },
                        onAlbumClick = { albumId -> viewModel.navigateTo(Screen.AlbumDetail(albumId)) },
                        onArtistClick = { artistId -> viewModel.navigateTo(Screen.ArtistDetail(artistId)) },
                        onPlaylistClick = { playlistId -> viewModel.navigateTo(Screen.PlaylistDetail(playlistId)) },
                        onLikeTrack = { trackId -> viewModel.toggleLikeTrack(trackId) },
                        onTrackMenuClick = { track -> viewModel.setTrackToAddToPlaylist(track) },
                        onDeviceClick = { viewModel.setAudioOutputSheetVisible(true) },
                        onEqualizerClick = { viewModel.setEqualizerSheetVisible(true) },
                        onShareClick = { viewModel.setQuickShareVisible(true) },
                        onSettingsClick = { viewModel.navigateTo(Screen.Settings) }
                    )
                }

                is Screen.Search -> {
                    SearchScreen(
                        searchQuery = searchQuery,
                        searchResults = searchResults,
                        searchHistory = searchHistory,
                        onQueryChange = { query -> viewModel.onSearchQueryChanged(query) },
                        onHistoryItemClick = { query ->
                            viewModel.onSearchQueryChanged(query)
                            viewModel.addSearchHistory(query)
                        },
                        onClearHistory = { viewModel.clearSearchHistory() },
                        onTrackClick = { track -> viewModel.playTrack(track, tracks) },
                        onAlbumClick = { albumId -> viewModel.navigateTo(Screen.AlbumDetail(albumId)) },
                        onArtistClick = { artistId -> viewModel.navigateTo(Screen.ArtistDetail(artistId)) },
                        onPlaylistClick = { playlistId -> viewModel.navigateTo(Screen.PlaylistDetail(playlistId)) }
                    )
                }

                is Screen.Library -> {
                    LibraryScreen(
                        playlists = playlists,
                        onPlaylistClick = { playlistId -> viewModel.navigateTo(Screen.PlaylistDetail(playlistId)) },
                        onCreatePlaylistClick = { viewModel.setCreatePlaylistVisible(true) },
                        onDeletePlaylist = { playlistId -> viewModel.deletePlaylist(playlistId) }
                    )
                }

                is Screen.Settings -> {
                    SettingsScreen(
                        isConnectedToOfficialApi = isConnectedToSpotifyApi,
                        batterySaverMode = batterySaverMode,
                        amoledPureBlack = amoledPureBlack,
                        samsungPalette = samsungPalette,
                        onBackClick = { viewModel.navigateBack() },
                        onSaveSpotifyToken = { token -> viewModel.setSpotifyToken(token) },
                        onToggleBatterySaver = { enabled -> viewModel.toggleBatterySaver(enabled) },
                        onToggleAmoledBlack = { enabled -> viewModel.toggleAmoledBlack(enabled) },
                        onSelectPalette = { palette -> viewModel.setSamsungPalette(palette) }
                    )
                }

                is Screen.AlbumDetail -> {
                    val album = viewModel.repository.getAlbumById(screen.albumId)
                    AlbumDetailScreen(
                        album = album,
                        tracks = tracks,
                        onBackClick = { viewModel.navigateBack() },
                        onTrackClick = { track, queue -> viewModel.playTrack(track, queue) },
                        onPlayAllClick = { queue -> if (queue.isNotEmpty()) viewModel.playTrack(queue[0], queue) },
                        onShuffleClick = { queue ->
                            if (queue.isNotEmpty()) {
                                viewModel.toggleShuffle()
                                viewModel.playTrack(queue.random(), queue)
                            }
                        },
                        onLikeTrack = { trackId -> viewModel.toggleLikeTrack(trackId) }
                    )
                }

                is Screen.ArtistDetail -> {
                    val artist = viewModel.repository.getArtistById(screen.artistId)
                    ArtistDetailScreen(
                        artist = artist,
                        tracks = tracks,
                        albums = albums,
                        onBackClick = { viewModel.navigateBack() },
                        onTrackClick = { track, queue -> viewModel.playTrack(track, queue) },
                        onAlbumClick = { albumId -> viewModel.navigateTo(Screen.AlbumDetail(albumId)) },
                        onFollowClick = { artistId -> viewModel.toggleArtistFollow(artistId) }
                    )
                }

                is Screen.PlaylistDetail -> {
                    val playlist = viewModel.repository.getPlaylistById(screen.playlistId)
                    PlaylistDetailScreen(
                        playlist = playlist,
                        allTracks = tracks,
                        onBackClick = { viewModel.navigateBack() },
                        onTrackClick = { track, queue -> viewModel.playTrack(track, queue) },
                        onPlayAllClick = { queue -> if (queue.isNotEmpty()) viewModel.playTrack(queue[0], queue) },
                        onShuffleClick = { queue ->
                            if (queue.isNotEmpty()) {
                                viewModel.toggleShuffle()
                                viewModel.playTrack(queue.random(), queue)
                            }
                        },
                        onLikeTrack = { trackId -> viewModel.toggleLikeTrack(trackId) },
                        onRemoveTrack = { plId, trId -> viewModel.removeTrackFromPlaylist(plId, trId) },
                        onDeletePlaylist = { plId -> viewModel.deletePlaylist(plId) }
                    )
                }
            }
        }
    }

    // Full Player Modal
    if (isFullPlayerVisible && currentTrack != null) {
        FullPlayerModal(
            track = currentTrack,
            isPlaying = isPlaying,
            currentPositionMs = currentPositionMs,
            durationMs = durationMs,
            isShuffle = isShuffle,
            isRepeat = isRepeat,
            activeDevice = activeDevice,
            activePreset = activePreset,
            dolbyAtmos = dolbyAtmos,
            onDismiss = { viewModel.setFullPlayerVisible(false) },
            onPlayPauseClick = { viewModel.togglePlayPause() },
            onNextClick = { viewModel.nextTrack() },
            onPreviousClick = { viewModel.previousTrack() },
            onSeek = { ms -> viewModel.seekTo(ms) },
            onShuffleToggle = { viewModel.toggleShuffle() },
            onRepeatToggle = { viewModel.toggleRepeat() },
            onLikeToggle = { id -> viewModel.toggleLikeTrack(id) },
            onDeviceOutputClick = { viewModel.setAudioOutputSheetVisible(true) },
            onEqualizerClick = { viewModel.setEqualizerSheetVisible(true) },
            onQuickShareClick = { viewModel.setQuickShareVisible(true) },
            onAddToPlaylistClick = { track -> viewModel.setTrackToAddToPlaylist(track) }
        )
    }

    // Audio Output Switcher (Galaxy Buds, Watch, TV)
    if (isAudioOutputSheetVisible) {
        AudioOutputSheet(
            devices = devices,
            activeDevice = activeDevice,
            onSelectDevice = { id -> viewModel.selectAudioDevice(id) },
            onToggleAnc = { id -> viewModel.toggleAnc(id) },
            onDismiss = { viewModel.setAudioOutputSheetVisible(false) }
        )
    }

    // Equalizer Sheet (Samsung SoundAlive & Dolby Atmos)
    if (isEqualizerSheetVisible) {
        EqualizerSheet(
            activePreset = activePreset,
            dolbyAtmos = dolbyAtmos,
            spatialAudio = spatialAudio,
            onSelectPreset = { preset -> viewModel.setSoundPreset(preset) },
            onToggleDolbyAtmos = { viewModel.toggleDolbyAtmos() },
            onToggleSpatialAudio = { viewModel.toggleSpatialAudio() },
            onDismiss = { viewModel.setEqualizerSheetVisible(false) }
        )
    }

    // Samsung Quick Share Dialog
    if (isQuickShareVisible) {
        QuickShareDialog(
            track = currentTrack,
            onDismiss = { viewModel.setQuickShareVisible(false) }
        )
    }

    // Create Playlist Dialog
    if (isCreatePlaylistVisible) {
        CreatePlaylistDialog(
            onDismiss = { viewModel.setCreatePlaylistVisible(false) },
            onCreate = { title, desc, color -> viewModel.createPlaylist(title, desc, color) }
        )
    }

    // Add Track to Playlist Dialog
    if (trackToAddToPlaylist != null) {
        AddToPlaylistDialog(
            track = trackToAddToPlaylist,
            playlists = playlists,
            onDismiss = { viewModel.setTrackToAddToPlaylist(null) },
            onSelectPlaylist = { plId ->
                trackToAddToPlaylist?.let { tr ->
                    viewModel.addTrackToPlaylist(plId, tr.id)
                }
            },
            onCreateNewClick = { viewModel.setCreatePlaylistVisible(true) }
        )
    }
}

@Composable
fun OneUiBottomNavigationBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    val navItems = listOf(
        NavigationItem("Início", Screen.Home, Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavigationItem("Buscar", Screen.Search, Icons.Filled.Search, Icons.Outlined.Search, "nav_search"),
        NavigationItem("Biblioteca", Screen.Library, Icons.Filled.LibraryMusic, Icons.Outlined.LibraryMusic, "nav_library"),
        NavigationItem("Ajustes", Screen.Settings, Icons.Filled.Settings, Icons.Outlined.Settings, "nav_settings")
    )

    NavigationBar(
        containerColor = OneUiDarkBackground,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = OneUiCardBorder)
            .testTag("one_ui_bottom_navigation")
    ) {
        navItems.forEach { item ->
            val isSelected = when (currentScreen) {
                is Screen.Home -> item.screen is Screen.Home
                is Screen.Search -> item.screen is Screen.Search
                is Screen.Library, is Screen.PlaylistDetail -> item.screen is Screen.Library
                is Screen.Settings -> item.screen is Screen.Settings
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.padding(2.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = OneUiTextMuted,
                    unselectedTextColor = OneUiTextMuted
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}

data class NavigationItem(
    val label: String,
    val screen: Screen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)
