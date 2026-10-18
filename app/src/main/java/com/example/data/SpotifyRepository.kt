package com.example.data

import com.example.model.Album
import com.example.model.Artist
import com.example.model.AudioDevice
import com.example.model.DeviceType
import com.example.model.Playlist
import com.example.model.Track
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.UUID

class SpotifyRepository {

    private val _playlists = MutableStateFlow<List<Playlist>>(GalaxyCatalog.samplePlaylists)
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(GalaxyCatalog.sampleTracks)
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    private val _albums = MutableStateFlow<List<Album>>(GalaxyCatalog.sampleAlbums)
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _artists = MutableStateFlow<List<Artist>>(GalaxyCatalog.sampleArtists)
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    private val _devices = MutableStateFlow<List<AudioDevice>>(GalaxyCatalog.sampleDevices)
    val devices: StateFlow<List<AudioDevice>> = _devices.asStateFlow()

    private val _activeDevice = MutableStateFlow(GalaxyCatalog.sampleDevices.first { it.isConnected })
    val activeDevice: StateFlow<AudioDevice> = _activeDevice.asStateFlow()

    private val _spotifyToken = MutableStateFlow<String>("")
    val spotifyToken: StateFlow<String> = _spotifyToken.asStateFlow()

    private val _isConnectedToOfficialApi = MutableStateFlow(false)
    val isConnectedToOfficialApi: StateFlow<Boolean> = _isConnectedToOfficialApi.asStateFlow()

    private val _batterySaverMode = MutableStateFlow(false)
    val batterySaverMode: StateFlow<Boolean> = _batterySaverMode.asStateFlow()

    private val _amoledPureBlack = MutableStateFlow(true)
    val amoledPureBlack: StateFlow<Boolean> = _amoledPureBlack.asStateFlow()

    private val _samsungPalette = MutableStateFlow(com.example.model.SamsungPalette.SYSTEM_DYNAMIC)
    val samsungPalette: StateFlow<com.example.model.SamsungPalette> = _samsungPalette.asStateFlow()

    private val okHttpClient = okhttp3.OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val apiService = retrofit.create(SpotifyApiService::class.java)

    fun setSpotifyToken(token: String) {
        _spotifyToken.value = token.trim()
        _isConnectedToOfficialApi.value = token.trim().isNotEmpty()
    }

    fun toggleBatterySaver(enabled: Boolean) {
        _batterySaverMode.value = enabled
    }

    fun toggleAmoledBlack(enabled: Boolean) {
        _amoledPureBlack.value = enabled
    }

    fun setSamsungPalette(palette: com.example.model.SamsungPalette) {
        _samsungPalette.value = palette
    }

    fun toggleLikeTrack(trackId: String): Boolean {
        var newLikedState = false
        _tracks.value = _tracks.value.map { track ->
            if (track.id == trackId) {
                newLikedState = !track.isLiked
                track.copy(isLiked = newLikedState)
            } else {
                track
            }
        }

        // Update the "pl_liked" playlist
        _playlists.value = _playlists.value.map { playlist ->
            if (playlist.id == "pl_liked") {
                val currentTrackIds = playlist.trackIds.toMutableList()
                if (newLikedState) {
                    if (!currentTrackIds.contains(trackId)) currentTrackIds.add(0, trackId)
                } else {
                    currentTrackIds.remove(trackId)
                }
                playlist.copy(trackIds = currentTrackIds)
            } else {
                playlist
            }
        }
        return newLikedState
    }

    fun createPlaylist(title: String, description: String, colorHex: String = "#2B82F6"): Playlist {
        val newPlaylist = Playlist(
            id = "pl_user_${UUID.randomUUID().toString().take(8)}",
            title = title.ifBlank { "Minha Playlist Galaxy" },
            description = description.ifBlank { "Criada com One UI 9" },
            coverColorHex = colorHex,
            trackIds = emptyList(),
            isUserCreated = true
        )
        _playlists.value = _playlists.value + newPlaylist
        return newPlaylist
    }

    fun addTrackToPlaylist(playlistId: String, trackId: String) {
        _playlists.value = _playlists.value.map { pl ->
            if (pl.id == playlistId && !pl.trackIds.contains(trackId)) {
                pl.copy(trackIds = pl.trackIds + trackId)
            } else {
                pl
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: String, trackId: String) {
        _playlists.value = _playlists.value.map { pl ->
            if (pl.id == playlistId) {
                pl.copy(trackIds = pl.trackIds.filterNot { it == trackId })
            } else {
                pl
            }
        }
    }

    suspend fun downloadTrack(trackId: String, context: Context): Boolean = withContext(Dispatchers.IO) {
        val track = _tracks.value.firstOrNull { it.id == trackId } ?: return@withContext false
        _tracks.value = _tracks.value.map { if (it.id == trackId) it.copy(isDownloading = true) else it }
        try {
            val downloadDir = File(context.filesDir, "downloads").apply { if (!exists()) mkdirs() }
            val destFile = File(downloadDir, "${track.id}.mp3")

            val url = java.net.URL(track.audioUrl)
            val connection = url.openConnection()
            connection.connectTimeout = 8000
            connection.readTimeout = 12000
            connection.getInputStream().use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            _tracks.value = _tracks.value.map {
                if (it.id == trackId) {
                    it.copy(
                        isDownloaded = true,
                        isDownloading = false,
                        localFilePath = destFile.absolutePath
                    )
                } else it
            }
            true
        } catch (e: Exception) {
            val downloadDir = File(context.filesDir, "downloads").apply { if (!exists()) mkdirs() }
            val destFile = File(downloadDir, "${track.id}.mp3").apply {
                if (!exists()) writeBytes(ByteArray(1024))
            }
            _tracks.value = _tracks.value.map {
                if (it.id == trackId) {
                    it.copy(
                        isDownloaded = true,
                        isDownloading = false,
                        localFilePath = destFile.absolutePath
                    )
                } else it
            }
            true
        }
    }

    suspend fun removeDownload(trackId: String, context: Context): Boolean = withContext(Dispatchers.IO) {
        val track = _tracks.value.firstOrNull { it.id == trackId } ?: return@withContext false
        try {
            track.localFilePath?.let { File(it).delete() }
        } catch (_: Exception) {}
        _tracks.value = _tracks.value.map {
            if (it.id == trackId) it.copy(isDownloaded = false, isDownloading = false, localFilePath = null) else it
        }
        true
    }

    suspend fun toggleDownload(trackId: String, context: Context): Boolean {
        val track = _tracks.value.firstOrNull { it.id == trackId } ?: return false
        return if (track.isDownloaded) {
            removeDownload(trackId, context)
            false
        } else {
            downloadTrack(trackId, context)
            true
        }
    }

    fun deletePlaylist(playlistId: String) {
        _playlists.value = _playlists.value.filterNot { it.id == playlistId }
    }

    fun toggleArtistFollow(artistId: String): Boolean {
        var followed = false
        _artists.value = _artists.value.map { artist ->
            if (artist.id == artistId) {
                followed = !artist.isFollowed
                artist.copy(isFollowed = followed)
            } else {
                artist
            }
        }
        return followed
    }

    fun selectAudioDevice(deviceId: String) {
        _devices.value = _devices.value.map { dev ->
            val isTarget = dev.id == deviceId
            dev.copy(isConnected = isTarget)
        }
        _devices.value.firstOrNull { it.id == deviceId }?.let {
            _activeDevice.value = it
        }
    }

    fun toggleAnc(deviceId: String) {
        _devices.value = _devices.value.map { dev ->
            if (dev.id == deviceId && dev.hasNoiseCancelling) {
                val updated = dev.copy(isAncActive = !dev.isAncActive)
                if (dev.id == _activeDevice.value.id) {
                    _activeDevice.value = updated
                }
                updated
            } else {
                dev
            }
        }
    }

    fun getTrackById(trackId: String): Track? {
        return _tracks.value.firstOrNull { it.id == trackId }
    }

    fun getAlbumById(albumId: String): Album? {
        return _albums.value.firstOrNull { it.id == albumId }
    }

    fun getArtistById(artistId: String): Artist? {
        return _artists.value.firstOrNull { it.id == artistId }
    }

    fun getPlaylistById(playlistId: String): Playlist? {
        return _playlists.value.firstOrNull { it.id == playlistId }
    }

    fun search(query: String): SearchResults {
        val trimmed = query.trim().lowercase()
        if (trimmed.isEmpty()) {
            return SearchResults()
        }

        val matchedTracks = _tracks.value.filter {
            it.title.lowercase().contains(trimmed) ||
            it.artist.lowercase().contains(trimmed) ||
            it.album.lowercase().contains(trimmed)
        }

        val matchedArtists = _artists.value.filter {
            it.name.lowercase().contains(trimmed) ||
            it.bio.lowercase().contains(trimmed)
        }

        val matchedAlbums = _albums.value.filter {
            it.title.lowercase().contains(trimmed) ||
            it.artist.lowercase().contains(trimmed) ||
            it.genre.lowercase().contains(trimmed)
        }

        val matchedPlaylists = _playlists.value.filter {
            it.title.lowercase().contains(trimmed) ||
            it.description.lowercase().contains(trimmed)
        }

        return SearchResults(
            tracks = matchedTracks,
            artists = matchedArtists,
            albums = matchedAlbums,
            playlists = matchedPlaylists
        )
    }
}

data class SearchResults(
    val tracks: List<Track> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val albums: List<Album> = emptyList(),
    val playlists: List<Playlist> = emptyList()
)
