package com.example.data

import com.example.model.Album
import com.example.model.Artist
import com.example.model.AudioDevice
import com.example.model.DeviceType
import com.example.model.LyricsLine
import com.example.model.Playlist
import com.example.model.Track

object GalaxyCatalog {

    val sampleTracks = listOf(
        Track(
            id = "track_1",
            title = "Midnight City Lights",
            artist = "The Weeknd & Daft Synth",
            artistId = "artist_weeknd",
            album = "After Hours Neon",
            albumId = "album_afterhours",
            durationMs = 186000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            coverUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500&q=80",
            playsCount = "89.4M",
            lyrics = listOf(
                LyricsLine(0L, "♪ Synthwave intro ♪"),
                LyricsLine(8000L, "City streets are shining in the midnight rain"),
                LyricsLine(16000L, "Neon reflections washing out the pain"),
                LyricsLine(24000L, "I hear the rhythm calling through the night"),
                LyricsLine(32000L, "Galaxy glowing in the sapphire light"),
                LyricsLine(40000L, "Take me where the music never ends"),
                LyricsLine(48000L, "Bass drop resonates through the lens"),
                LyricsLine(58000L, "♪ Energetic synth solo ♪"),
                LyricsLine(80000L, "We ride until the morning comes alive")
            ),
            isLiked = true
        ),
        Track(
            id = "track_2",
            title = "Starboy Echoes",
            artist = "The Weeknd",
            artistId = "artist_weeknd",
            album = "Starboy Deluxe",
            albumId = "album_starboy",
            durationMs = 210000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            coverUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500&q=80",
            playsCount = "124.8M",
            lyrics = listOf(
                LyricsLine(0L, "♪ Deep bass groove ♪"),
                LyricsLine(10000L, "I'm trying to put you in the worst mood, ah"),
                LyricsLine(18000L, "P1 cleaner than your church shoes, ah"),
                LyricsLine(26000L, "Milli point two just to hurt you, ah"),
                LyricsLine(35000L, "All red Lamb' just to tease you, ah"),
                LyricsLine(44000L, "Look what you've done"),
                LyricsLine(52000L, "I'm a mother**** starboy!"),
                LyricsLine(62000L, "♪ Electronic beat drop ♪")
            ),
            isLiked = true
        ),
        Track(
            id = "track_3",
            title = "Cruel Summer Breeze",
            artist = "Taylor Swift",
            artistId = "artist_taylor",
            album = "Lover (Galaxy Edition)",
            albumId = "album_lover",
            durationMs = 178000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            coverUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500&q=80",
            playsCount = "215.1M",
            lyrics = listOf(
                LyricsLine(0L, "Fever dream high in the quiet of the night"),
                LyricsLine(12000L, "You know that I caught it"),
                LyricsLine(20000L, "Bad, bad boy, shiny toy with a price"),
                LyricsLine(28000L, "You know that I bought it"),
                LyricsLine(36000L, "Killing me slow, out the window"),
                LyricsLine(44000L, "I'm always waiting for you to be waiting below"),
                LyricsLine(52000L, "And it's new, the shape of your body"),
                LyricsLine(60000L, "It's blue, the feeling I've got"),
                LyricsLine(68000L, "It's a cruel summer!")
            ),
            isLiked = false
        ),
        Track(
            id = "track_4",
            title = "Birds of a Feather",
            artist = "Billie Eilish",
            artistId = "artist_billie",
            album = "HIT ME HARD AND SOFT",
            albumId = "album_billie",
            durationMs = 195000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            coverUrl = "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500&q=80",
            playsCount = "178.6M",
            lyrics = listOf(
                LyricsLine(0L, "I want you to stay"),
                LyricsLine(8000L, "'Til I'm in the grave"),
                LyricsLine(15000L, "'Til I rot, away, dead and buried"),
                LyricsLine(23000L, "'Til I'm in the casket you carry"),
                LyricsLine(31000L, "If you go, I'm going too, uh"),
                LyricsLine(38000L, "'Cause it was always you, alright"),
                LyricsLine(46000L, "And if I'm turnin' blue, please don't save me"),
                LyricsLine(54000L, "Nothing in this world could persuade me")
            ),
            isLiked = true
        ),
        Track(
            id = "track_5",
            title = "One More Time (Galaxy Remix)",
            artist = "Daft Punk",
            artistId = "artist_daft",
            album = "Discovery Remastered",
            albumId = "album_discovery",
            durationMs = 240000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            coverUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=500&q=80",
            playsCount = "310.4M",
            lyrics = listOf(
                LyricsLine(0L, "One more time, we're gonna celebrate"),
                LyricsLine(8000L, "Oh yeah, alright, don't stop the dancing"),
                LyricsLine(16000L, "One more time, we're gonna celebrate"),
                LyricsLine(24000L, "Oh yeah, alright, don't stop the dancing"),
                LyricsLine(32000L, "Music's got me feeling so free"),
                LyricsLine(40000L, "We're gonna celebrate!"),
                LyricsLine(48000L, "Celebrate and dance so free"),
                LyricsLine(56000L, "One more time!")
            ),
            isLiked = true
        ),
        Track(
            id = "track_6",
            title = "Monaco Beats",
            artist = "Bad Bunny",
            artistId = "artist_badbunny",
            album = "Nadie Sabe Lo Que Va a Pasar",
            albumId = "album_monaco",
            durationMs = 205000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
            coverUrl = "https://images.unsplash.com/photo-1487180144351-b8472da7d491?w=500&q=80",
            playsCount = "142.0M",
            lyrics = listOf(
                LyricsLine(0L, "Dime si te acuerdas de mí"),
                LyricsLine(12000L, "Bebiendo champagne en Mónaco"),
                LyricsLine(22000L, "El dinero no compra la felicidad, pero ayuda"),
                LyricsLine(32000L, "Subiendo el volumen en los Galaxy Buds"),
                LyricsLine(42000L, "Ahora los carros son todos de carreras"),
                LyricsLine(52000L, "♪ Trap Beat drop ♪")
            ),
            isLiked = false
        ),
        Track(
            id = "track_7",
            title = "As It Was",
            artist = "Harry Styles",
            artistId = "artist_harry",
            album = "Harry's House",
            albumId = "album_harrys_house",
            durationMs = 167000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3",
            coverUrl = "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=500&q=80",
            playsCount = "290.7M",
            lyrics = listOf(
                LyricsLine(0L, "Hold on, ring-ring, goes the phone"),
                LyricsLine(10000L, "You know it's not the same as it was"),
                LyricsLine(18000L, "In this world, it's just us"),
                LyricsLine(26000L, "You know it's not the same as it was"),
                LyricsLine(34000L, "As it was, as it was"),
                LyricsLine(42000L, "You know it's not the same!")
            ),
            isLiked = true
        ),
        Track(
            id = "track_8",
            title = "Espresso Mood",
            artist = "Sabrina Carpenter",
            artistId = "artist_sabrina",
            album = "Short n' Sweet",
            albumId = "album_sweet",
            durationMs = 175000L,
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
            coverUrl = "https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=500&q=80",
            playsCount = "188.3M",
            lyrics = listOf(
                LyricsLine(0L, "Now he's thinkin' 'bout me every night, oh"),
                LyricsLine(10000L, "Is it that sweet? I guess so"),
                LyricsLine(18000L, "Say you can't sleep, baby, I know"),
                LyricsLine(26000L, "That's that me, espresso"),
                LyricsLine(34000L, "Move it up, down, left, right, oh"),
                LyricsLine(42000L, "Switch it up like Nintendo")
            ),
            isLiked = true
        )
    )

    val sampleAlbums = listOf(
        Album(
            id = "album_afterhours",
            title = "After Hours Neon",
            artist = "The Weeknd & Daft Synth",
            artistId = "artist_weeknd",
            releaseYear = 2024,
            coverUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500&q=80",
            genre = "Synth-Pop / R&B",
            recordLabel = "XO / Republic Records",
            trackIds = listOf("track_1")
        ),
        Album(
            id = "album_starboy",
            title = "Starboy Deluxe",
            artist = "The Weeknd",
            artistId = "artist_weeknd",
            releaseYear = 2023,
            coverUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500&q=80",
            genre = "Electro-Pop",
            recordLabel = "XO Records",
            trackIds = listOf("track_2")
        ),
        Album(
            id = "album_lover",
            title = "Lover (Galaxy Edition)",
            artist = "Taylor Swift",
            artistId = "artist_taylor",
            releaseYear = 2023,
            coverUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500&q=80",
            genre = "Pop / Indie",
            recordLabel = "Republic Records",
            trackIds = listOf("track_3")
        ),
        Album(
            id = "album_billie",
            title = "HIT ME HARD AND SOFT",
            artist = "Billie Eilish",
            artistId = "artist_billie",
            releaseYear = 2024,
            coverUrl = "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500&q=80",
            genre = "Alt-Pop / Dream Pop",
            recordLabel = "Darkroom / Interscope",
            trackIds = listOf("track_4")
        ),
        Album(
            id = "album_discovery",
            title = "Discovery Remastered",
            artist = "Daft Punk",
            artistId = "artist_daft",
            releaseYear = 2023,
            coverUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=500&q=80",
            genre = "Electronic / French Touch",
            recordLabel = "Daft Life / Warner",
            trackIds = listOf("track_5")
        )
    )

    val sampleArtists = listOf(
        Artist(
            id = "artist_weeknd",
            name = "The Weeknd",
            monthlyListeners = "109.812.440 ouvintes mensais",
            bio = "Abel Makkonen Tesfaye, conhecido como The Weeknd, é pioneiro no renascimento do R&B e Synthwave cinematográfico contemporâneo com produções premiadas com múltiplos Grammy Awards.",
            avatarUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500&q=80",
            topTrackIds = listOf("track_1", "track_2"),
            albumIds = listOf("album_afterhours", "album_starboy"),
            isFollowed = true
        ),
        Artist(
            id = "artist_taylor",
            name = "Taylor Swift",
            monthlyListeners = "118.450.320 ouvintes mensais",
            bio = "Uma das maiores compositoras e artistas da história da música pop moderna, com turnês mundiais com lotação esgotada e álbuns aclamados globalmente.",
            avatarUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500&q=80",
            topTrackIds = listOf("track_3"),
            albumIds = listOf("album_lover"),
            isFollowed = true
        ),
        Artist(
            id = "artist_billie",
            name = "Billie Eilish",
            monthlyListeners = "96.240.100 ouvintes mensais",
            bio = "Cantora e compositora vencedora de Oscars e Grammys, conhecida por sua sonoridade intimista, inovação na mixagem espacial e vocais comoventes.",
            avatarUrl = "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500&q=80",
            topTrackIds = listOf("track_4"),
            albumIds = listOf("album_billie"),
            isFollowed = false
        ),
        Artist(
            id = "artist_daft",
            name = "Daft Punk",
            monthlyListeners = "45.120.900 ouvintes mensais",
            bio = "A lendária dupla de música eletrônica francesa que redefiniu o house, funk e disco mundialmente com seus icônicos capacetes e batidas atemporais.",
            avatarUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=500&q=80",
            topTrackIds = listOf("track_5"),
            albumIds = listOf("album_discovery"),
            isFollowed = true
        ),
        Artist(
            id = "artist_badbunny",
            name = "Bad Bunny",
            monthlyListeners = "82.900.500 ouvintes mensais",
            bio = "Superestrela global porto-riquenha liderando o trap e reggaeton mundial no topo do Spotify por múltiplos anos consecutivos.",
            avatarUrl = "https://images.unsplash.com/photo-1487180144351-b8472da7d491?w=500&q=80",
            topTrackIds = listOf("track_6"),
            albumIds = listOf("album_monaco"),
            isFollowed = false
        )
    )

    val samplePlaylists = listOf(
        Playlist(
            id = "pl_liked",
            title = "Músicas Curtidas",
            description = "Suas músicas favoritas sincronizadas com o Spotify e Galaxy",
            coverColorHex = "#1DB954",
            trackIds = listOf("track_1", "track_2", "track_4", "track_5", "track_7", "track_8"),
            isUserCreated = false
        ),
        Playlist(
            id = "pl_daily_galaxy",
            title = "Galaxy Daily Mix",
            description = "Seleção diária com áudio espacial 360 e Dolby Atmos",
            coverColorHex = "#2B82F6",
            trackIds = listOf("track_1", "track_3", "track_5", "track_7"),
            isUserCreated = false
        ),
        Playlist(
            id = "pl_top_brasil",
            title = "Top 50 Brasil",
            description = "As faixas mais tocadas no Spotify Brasil atualizadas hoje",
            coverColorHex = "#10B981",
            trackIds = listOf("track_6", "track_7", "track_8", "track_2"),
            isUserCreated = false
        ),
        Playlist(
            id = "pl_chill_night",
            title = "One UI Midnight Vibes",
            description = "Batidas suaves e lo-fi para relaxar no ecossistema Samsung",
            coverColorHex = "#8B5CF6",
            trackIds = listOf("track_4", "track_1", "track_8"),
            isUserCreated = false
        )
    )

    val sampleDevices = listOf(
        AudioDevice(
            id = "dev_buds",
            name = "Galaxy Buds3 Pro",
            type = DeviceType.GALAXY_BUDS,
            batteryPercent = 94,
            isConnected = true,
            hasNoiseCancelling = true,
            isAncActive = true
        ),
        AudioDevice(
            id = "dev_speaker",
            name = "Alto-falante do Galaxy",
            type = DeviceType.PHONE_SPEAKER,
            batteryPercent = null,
            isConnected = false,
            hasNoiseCancelling = false,
            isAncActive = false
        ),
        AudioDevice(
            id = "dev_watch",
            name = "Galaxy Watch7 (Bluetooth)",
            type = DeviceType.GALAXY_WATCH,
            batteryPercent = 78,
            isConnected = false,
            hasNoiseCancelling = false,
            isAncActive = false
        ),
        AudioDevice(
            id = "dev_tv",
            name = "Samsung Neo QLED 4K (Smart View)",
            type = DeviceType.SAMSUNG_TV,
            batteryPercent = null,
            isConnected = false,
            hasNoiseCancelling = false,
            isAncActive = false
        )
    )
}
