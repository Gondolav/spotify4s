package com.github.gondolav.spotify4s.entities

import upickle.default._

private[spotify4s] case class RecommendationsJson(seeds: List[RecommendationSeed], tracks: List[TrackJson])

private[spotify4s] object RecommendationsJson {
  implicit val rw: ReadWriter[RecommendationsJson] = macroRW
}

case class Recommendations(seeds: List[RecommendationSeed], tracks: List[Track])

object Recommendations {
  private[spotify4s] def fromJson(json: RecommendationsJson): Recommendations = Recommendations(json.seeds, json.tracks.map(Track.fromJson))
}
