package com.github.gondolav.spotify4s.entities

import upickle.default._

case class RecommendationsJson(seeds: RecommendationSeed, tracks: List[TrackJson])
object RecommendationsJson {
  implicit val rw: ReadWriter[RecommendationsJson] = macroRW
}

case class Recommendations(seeds: RecommendationSeed, tracks: List[Track])
object Recommendations {
  def fromJson(json: RecommendationsJson): Recommendations = Recommendations(json.seeds, json.tracks.map(Track.fromJson))
}
