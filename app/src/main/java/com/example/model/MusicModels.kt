package com.example.model

data class LyricsLine(
    val timeMs: Long,
    val text: String
)

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val artistId: String,
    val album: String,
    val albumId: String,
    val durationMs: Long,
    val audioUrl: String,
    val coverUrl: String = "",
    val coverResId: Int? = null,
    val lyrics: List<LyricsLine> = emptyList(),
    val isLiked: Boolean = false,
    val playsCount: String = "1.2M",
    val explicit: Boolean = false,
    val isDownloaded: Boolean = false,
    val localFilePath: String? = null,
    val isDownloading: Boolean = false
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val artistId: String,
    val releaseYear: Int,
    val coverUrl: String = "",
    val coverResId: Int? = null,
    val genre: String,
    val recordLabel: String,
    val trackIds: List<String> = emptyList()
)

data class Artist(
    val id: String,
    val name: String,
    val monthlyListeners: String,
    val bio: String,
    val avatarUrl: String = "",
    val topTrackIds: List<String> = emptyList(),
    val albumIds: List<String> = emptyList(),
    val isFollowed: Boolean = false
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String,
    val coverColorHex: String = "#1DB954",
    val trackIds: List<String> = emptyList(),
    val isUserCreated: Boolean = false
)

enum class DeviceType {
    GALAXY_BUDS,
    GALAXY_WATCH,
    SAMSUNG_TV,
    PHONE_SPEAKER
}

data class AudioDevice(
    val id: String,
    val name: String,
    val type: DeviceType,
    val batteryPercent: Int? = null,
    val isConnected: Boolean = false,
    val hasNoiseCancelling: Boolean = false,
    val isAncActive: Boolean = false
)

enum class SoundAlivePreset(val label: String, val description: String) {
    AUTO("Otimizado Galaxy", "Ajuste dinâmico com IA Samsung"),
    POP("Pop & Vocais", "Vocais nítidos e agudos destacados"),
    ROCK("Rock & Dinâmico", "Médios presentes e guitarra definida"),
    CLASSICAL("Clássica & Acústica", "Palco sonoro estéreo ampliado"),
    JAZZ("Jazz Club", "Quente e intimista"),
    BASS_BOOST("Graves Potentes", "Sub-graves reforçados para Galaxy Buds")
}

enum class SamsungPalette(val label: String, val primaryHex: String) {
    SYSTEM_DYNAMIC("Material You (Sistema)", "#388BFD"),
    SAMSUNG_BLUE("Azul Galaxy Clássico", "#2B82F6"),
    SPOTIFY_GREEN("Verde Spotify", "#1ED760"),
    GALAXY_PURPLE("Violeta Bespoke", "#A855F7"),
    GALAXY_AMBER("Âmbar Dourado", "#F59E0B"),
    GALAXY_MINT("Menta Galaxy", "#10B981")
}
