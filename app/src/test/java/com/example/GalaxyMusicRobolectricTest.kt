package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.GalaxyCatalog
import com.example.data.SpotifyRepository
import com.example.model.DeviceType
import com.example.model.SoundAlivePreset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class GalaxyMusicRobolectricTest {

    @Test
    fun `verify app name resource matches Galaxy Music`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Galaxy Music", appName)
    }

    @Test
    fun `verify initial catalog tracks and playlists are loaded`() {
        val repository = SpotifyRepository()
        val tracks = repository.tracks.value
        val playlists = repository.playlists.value

        assertTrue(tracks.isNotEmpty())
        assertTrue(playlists.isNotEmpty())
        assertNotNull(repository.getTrackById("track_1"))
        assertNotNull(repository.getPlaylistById("pl_liked"))
    }

    @Test
    fun `verify track like toggle updates liked songs playlist`() {
        val repository = SpotifyRepository()
        val track = repository.tracks.value.first { !it.isLiked }
        val initialLiked = track.isLiked

        val newStatus = repository.toggleLikeTrack(track.id)
        assertTrue(newStatus != initialLiked)

        val updatedLikedPlaylist = repository.getPlaylistById("pl_liked")
        assertNotNull(updatedLikedPlaylist)
        if (newStatus) {
            assertTrue(updatedLikedPlaylist!!.trackIds.contains(track.id))
        }
    }

    @Test
    fun `verify playlist creation and track modification`() {
        val repository = SpotifyRepository()
        val newPlaylist = repository.createPlaylist(
            title = "Treino Galaxy",
            description = "Playlist energética",
            colorHex = "#2B82F6"
        )

        assertEquals("Treino Galaxy", newPlaylist.title)
        assertTrue(repository.playlists.value.any { it.id == newPlaylist.id })

        repository.addTrackToPlaylist(newPlaylist.id, "track_1")
        val updated = repository.getPlaylistById(newPlaylist.id)
        assertNotNull(updated)
        assertTrue(updated!!.trackIds.contains("track_1"))

        repository.removeTrackFromPlaylist(newPlaylist.id, "track_1")
        val afterRemoval = repository.getPlaylistById(newPlaylist.id)
        assertFalse(afterRemoval!!.trackIds.contains("track_1"))

        repository.deletePlaylist(newPlaylist.id)
        assertFalse(repository.playlists.value.any { it.id == newPlaylist.id })
    }

    @Test
    fun `verify search returns matched tracks, artists, and albums`() {
        val repository = SpotifyRepository()
        val searchWeeknd = repository.search("Weeknd")

        assertTrue(searchWeeknd.tracks.isNotEmpty())
        assertTrue(searchWeeknd.artists.isNotEmpty())
        assertEquals("The Weeknd", searchWeeknd.artists[0].name)
    }

    @Test
    fun `verify galaxy device output switching and ANC controls`() {
        val repository = SpotifyRepository()
        val buds = repository.devices.value.first { it.type == DeviceType.GALAXY_BUDS }

        repository.selectAudioDevice(buds.id)
        assertEquals(buds.id, repository.activeDevice.value.id)

        val initialAnc = repository.activeDevice.value.isAncActive
        repository.toggleAnc(buds.id)
        assertEquals(!initialAnc, repository.activeDevice.value.isAncActive)
    }

    @Test
    fun `verify battery saver and amoled dark mode toggles`() {
        val repository = SpotifyRepository()
        assertFalse(repository.batterySaverMode.value)

        repository.toggleBatterySaver(true)
        assertTrue(repository.batterySaverMode.value)

        repository.toggleAmoledBlack(true)
        assertTrue(repository.amoledPureBlack.value)
    }

    @Test
    fun `verify samsung material you color palette selection`() {
        val repository = SpotifyRepository()
        assertEquals(com.example.model.SamsungPalette.SYSTEM_DYNAMIC, repository.samsungPalette.value)

        repository.setSamsungPalette(com.example.model.SamsungPalette.SAMSUNG_BLUE)
        assertEquals(com.example.model.SamsungPalette.SAMSUNG_BLUE, repository.samsungPalette.value)

        repository.setSamsungPalette(com.example.model.SamsungPalette.SPOTIFY_GREEN)
        assertEquals(com.example.model.SamsungPalette.SPOTIFY_GREEN, repository.samsungPalette.value)
    }
}
