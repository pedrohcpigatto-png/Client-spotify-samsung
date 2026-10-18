package com.example.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApiService {

    @GET("v1/me")
    suspend fun getCurrentUser(
        @Header("Authorization") authHeader: String
    ): Response<Map<String, Any>>

    @GET("v1/search")
    suspend fun search(
        @Header("Authorization") authHeader: String,
        @Query("q") query: String,
        @Query("type") type: String = "track,artist,album,playlist",
        @Query("limit") limit: Int = 20
    ): Response<Map<String, Any>>

    @GET("v1/artists/{id}")
    suspend fun getArtist(
        @Header("Authorization") authHeader: String,
        @Path("id") artistId: String
    ): Response<Map<String, Any>>

    @GET("v1/artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(
        @Header("Authorization") authHeader: String,
        @Path("id") artistId: String,
        @Query("market") market: String = "BR"
    ): Response<Map<String, Any>>

    @GET("v1/albums/{id}")
    suspend fun getAlbum(
        @Header("Authorization") authHeader: String,
        @Path("id") albumId: String
    ): Response<Map<String, Any>>

    @GET("v1/me/playlists")
    suspend fun getUserPlaylists(
        @Header("Authorization") authHeader: String,
        @Query("limit") limit: Int = 20
    ): Response<Map<String, Any>>

    @POST("v1/users/{userId}/playlists")
    suspend fun createPlaylist(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: String,
        @Body body: Map<String, Any>
    ): Response<Map<String, Any>>

    @PUT("v1/me/player/play")
    suspend fun play(
        @Header("Authorization") authHeader: String,
        @Body body: Map<String, Any>? = null
    ): Response<Unit>

    @PUT("v1/me/player/pause")
    suspend fun pause(
        @Header("Authorization") authHeader: String
    ): Response<Unit>

    @POST("v1/me/player/next")
    suspend fun next(
        @Header("Authorization") authHeader: String
    ): Response<Unit>

    @POST("v1/me/player/previous")
    suspend fun previous(
        @Header("Authorization") authHeader: String
    ): Response<Unit>
}
