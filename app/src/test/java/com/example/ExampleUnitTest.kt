package com.example

import com.example.data.SpotifyRepository
import com.example.model.SoundAlivePreset
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var repository: SpotifyRepository

    @Before
    fun setUp() {
        repository = SpotifyRepository()
    }

    @Test
    fun testInitialTracksAndPlaylistsLoaded() = runBlocking {
        val tracks = repository.tracks.first()
        val playlists = repository.playlists.first()
        val albums = repository.albums.first()
        val artists = repository.artists.first()

        assertTrue("Initial tracks must not be empty", tracks.isNotEmpty())
        assertTrue("Initial playlists must not be empty", playlists.isNotEmpty())
        assertTrue("Initial albums must not be empty", albums.isNotEmpty())
        assertTrue("Initial artists must not be empty", artists.isNotEmpty())
    }

    @Test
    fun testPlaylistCreationAndTrackManagement() = runBlocking {
        val newPlaylist = repository.createPlaylist(
            title = "Galaxy Chill Hits",
            description = "Playlist One UI 9 personalizada",
            colorHex = "#2B82F6"
        )
        assertNotNull(newPlaylist.id)
        assertEquals("Galaxy Chill Hits", newPlaylist.title)

        val tracks = repository.tracks.first()
        val firstTrackId = tracks.first().id

        // Add track to playlist
        repository.addTrackToPlaylist(newPlaylist.id, firstTrackId)
        var updatedPlaylist = repository.playlists.first().first { it.id == newPlaylist.id }
        assertTrue("Playlist should contain added track", updatedPlaylist.trackIds.contains(firstTrackId))

        // Remove track from playlist
        repository.removeTrackFromPlaylist(newPlaylist.id, firstTrackId)
        updatedPlaylist = repository.playlists.first().first { it.id == newPlaylist.id }
        assertFalse("Playlist should not contain removed track", updatedPlaylist.trackIds.contains(firstTrackId))
    }

    @Test
    fun testSearchFunctionality() = runBlocking {
        val query = "Weeknd"
        val results = repository.search(query)

        assertTrue(
            "Search results should return matching artist or track",
            results.artists.any { it.name.contains("Weeknd", ignoreCase = true) } ||
            results.tracks.any { it.artist.contains("Weeknd", ignoreCase = true) }
        )
    }

    @Test
    fun testToggleLikeTrack() = runBlocking {
        val tracks = repository.tracks.first()
        val testTrack = tracks.first()
        val initialLiked = testTrack.isLiked

        val newLikedState = repository.toggleLikeTrack(testTrack.id)
        assertEquals(!initialLiked, newLikedState)

        val updatedTracks = repository.tracks.first()
        val updatedTrack = updatedTracks.first { it.id == testTrack.id }
        assertEquals(!initialLiked, updatedTrack.isLiked)
    }

    @Test
    fun testSoundAlivePresetsAvailable() {
        val presets = SoundAlivePreset.values()
        assertTrue("SoundAlive must have at least 5 Samsung presets", presets.size >= 5)
        assertTrue(presets.any { it.label.contains("Pop", ignoreCase = true) })
        assertTrue(presets.any { it.label.contains("Clássica", ignoreCase = true) })
        assertTrue(presets.any { it.label.contains("Graves", ignoreCase = true) })
        assertTrue(presets.any { it.label.contains("Galaxy", ignoreCase = true) })
    }
}
