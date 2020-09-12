package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

private[spotify4s] case class AudioFeaturesJson(
                                                 duration_ms: Int,
                                                 key: Int,
                                                 mode: Int,
                                                 time_signature: Int,
                                                 acousticness: Double,
                                                 danceability: Double,
                                                 energy: Double,
                                                 instrumentalness: Double,
                                                 liveness: Double,
                                                 loudness: Double,
                                                 speechiness: Double,
                                                 valence: Double,
                                                 tempo: Double,
                                                 id: String,
                                                 uri: String,
                                                 track_href: String,
                                                 analysis_url: String,
                                                 `type`: String
                                               )

private[spotify4s] object AudioFeaturesJson {
  implicit val rw: ReadWriter[AudioFeaturesJson] = macroRW
}

case class AudioFeatures(
                          durationMs: Int,
                          key: Int,
                          mode: Int,
                          timeSignature: Int,
                          acousticness: Double,
                          danceability: Double,
                          energy: Double,
                          instrumentalness: Double,
                          liveness: Double,
                          loudness: Double,
                          speechiness: Double,
                          valence: Double,
                          tempo: Double,
                          id: String,
                          uri: URI,
                          trackHref: String,
                          analysisURL: String,
                          objectType: String
                        )

object AudioFeatures {
  private[spotify4s] def fromJson(json: AudioFeaturesJson): AudioFeatures = AudioFeatures(
    json.duration_ms,
    json.key,
    json.mode,
    json.time_signature,
    json.acousticness,
    json.danceability,
    json.energy,
    json.instrumentalness,
    json.liveness,
    json.loudness,
    json.speechiness,
    json.valence,
    json.tempo,
    json.id,
    URI.create(json.uri),
    json.track_href,
    json.analysis_url,
    json.`type`
  )
}
