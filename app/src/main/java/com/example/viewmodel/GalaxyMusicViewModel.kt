package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SearchResults
import com.example.data.SpotifyRepository
import com.example.model.Album
import com.example.model.Artist
import com.example.model.AudioDevice
import com.example.model.Playlist
import com.example.model.SoundAlivePreset
import com.example.model.Track
import com.example.player.GalaxyAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    data object Home : Screen()
    data object Search : Screen()
    data object Library : Screen()
    data object Settings : Screen()
    data class AlbumDetail(val albumId: String) : Screen()
    data class ArtistDetail(val artistId: String) : Screen()
    data class PlaylistDetail(val playlistId: String) : Screen()
}

class GalaxyMusicViewModel(application: Application) : AndroidViewModel(application) {

    val repository = SpotifyRepository()
    val audioPlayer = GalaxyAudioPlayer(application.applicationContext)

    // Screen navigation stack
    private val screenStack = mutableListOf<Screen>(Screen.Home)
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Modals & Bottom Sheets
    private val _isFullPlayerVisible = MutableStateFlow(false)
    val isFullPlayerVisible: StateFlow<Boolean> = _isFullPlayerVisible.asStateFlow()

    private val _isAudioOutputSheetVisible = MutableStateFlow(false)
    val isAudioOutputSheetVisible: StateFlow<Boolean> = _isAudioOutputSheetVisible.asStateFlow()

    private val _isEqualizerSheetVisible = MutableStateFlow(false)
    val isEqualizerSheetVisible: StateFlow<Boolean> = _isEqualizerSheetVisible.asStateFlow()

    private val _isQuickShareVisible = MutableStateFlow(false)
    val isQuickShareVisible: StateFlow<Boolean> = _isQuickShareVisible.asStateFlow()

    private val _isCreatePlaylistVisible = MutableStateFlow(false)
    val isCreatePlaylistVisible: StateFlow<Boolean> = _isCreatePlaylistVisible.asStateFlow()

    private val _trackToAddToPlaylist = MutableStateFlow<Track?>(null)
    val trackToAddToPlaylist: StateFlow<Track?> = _trackToAddToPlaylist.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow(SearchResults())
    val searchResults: StateFlow<SearchResults> = _searchResults.asStateFlow()

    private val _searchHistory = MutableStateFlow(listOf("The Weeknd", "Taylor Swift", "Synthwave", "Top Brasil"))
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    // Battery / Galaxy Telemetry & Material You
    val batterySaverMode = repository.batterySaverMode
    val amoledPureBlack = repository.amoledPureBlack
    val samsungPalette = repository.samsungPalette

    // Data from repo
    val tracks = repository.tracks
    val playlists = repository.playlists
    val albums = repository.albums
    val artists = repository.artists
    val devices = repository.devices
    val activeDevice = repository.activeDevice
    val isConnectedToSpotifyApi = repository.isConnectedToOfficialApi

    init {
        // Initialize player with first track
        val initialTracks = repository.tracks.value
        if (initialTracks.isNotEmpty()) {
            audioPlayer.setQueue(initialTracks, 0)
            audioPlayer.pause()
        }
    }

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        return if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.lastIndex)
            _currentScreen.value = screenStack.last()
            true
        } else {
            false
        }
    }

    fun setFullPlayerVisible(visible: Boolean) {
        _isFullPlayerVisible.value = visible
    }

    fun setAudioOutputSheetVisible(visible: Boolean) {
        _isAudioOutputSheetVisible.value = visible
    }

    fun setEqualizerSheetVisible(visible: Boolean) {
        _isEqualizerSheetVisible.value = visible
    }

    fun setQuickShareVisible(visible: Boolean) {
        _isQuickShareVisible.value = visible
    }

    fun setCreatePlaylistVisible(visible: Boolean) {
        _isCreatePlaylistVisible.value = visible
    }

    fun setTrackToAddToPlaylist(track: Track?) {
        _trackToAddToPlaylist.value = track
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _searchResults.value = repository.search(query)
    }

    fun addSearchHistory(term: String) {
        if (term.isNotBlank()) {
            val list = _searchHistory.value.toMutableList()
            list.remove(term)
            list.add(0, term)
            _searchHistory.value = list.take(8)
        }
    }

    fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    fun playTrack(track: Track, queue: List<Track> = repository.tracks.value) {
        val index = queue.indexOfFirst { it.id == track.id }.coerceAtLeast(0)
        audioPlayer.setQueue(queue, index)
    }

    fun togglePlayPause() {
        audioPlayer.togglePlayPause()
    }

    fun nextTrack() {
        audioPlayer.nextTrack()
    }

    fun previousTrack() {
        audioPlayer.previousTrack()
    }

    fun seekTo(ms: Long) {
        audioPlayer.seekTo(ms)
    }

    fun toggleShuffle() {
        audioPlayer.toggleShuffle()
    }

    fun toggleRepeat() {
        audioPlayer.toggleRepeat()
    }

    fun toggleLikeTrack(trackId: String) {
        repository.toggleLikeTrack(trackId)
    }

    fun toggleArtistFollow(artistId: String) {
        repository.toggleArtistFollow(artistId)
    }

    fun createPlaylist(title: String, description: String, colorHex: String) {
        repository.createPlaylist(title, description, colorHex)
        _isCreatePlaylistVisible.value = false
    }

    fun addTrackToPlaylist(playlistId: String, trackId: String) {
        repository.addTrackToPlaylist(playlistId, trackId)
        _trackToAddToPlaylist.value = null
    }

    fun removeTrackFromPlaylist(playlistId: String, trackId: String) {
        repository.removeTrackFromPlaylist(playlistId, trackId)
    }

    fun deletePlaylist(playlistId: String) {
        repository.deletePlaylist(playlistId)
        if (_currentScreen.value is Screen.PlaylistDetail) {
            navigateBack()
        }
    }

    fun selectAudioDevice(deviceId: String) {
        repository.selectAudioDevice(deviceId)
    }

    fun toggleAnc(deviceId: String) {
        repository.toggleAnc(deviceId)
    }

    fun setSoundPreset(preset: SoundAlivePreset) {
        audioPlayer.setSoundPreset(preset)
    }

    fun toggleDolbyAtmos() {
        audioPlayer.toggleDolbyAtmos()
    }

    fun toggleSpatialAudio() {
        audioPlayer.toggleSpatialAudio()
    }

    fun toggleBatterySaver(enabled: Boolean) {
        repository.toggleBatterySaver(enabled)
    }

    fun toggleAmoledBlack(enabled: Boolean) {
        repository.toggleAmoledBlack(enabled)
    }

    fun setSpotifyToken(token: String) {
        repository.setSpotifyToken(token)
    }

    fun setSamsungPalette(palette: com.example.model.SamsungPalette) {
        repository.setSamsungPalette(palette)
    }

    fun toggleDownloadTrack(trackId: String) {
        viewModelScope.launch {
            repository.toggleDownload(trackId, getApplication())
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
