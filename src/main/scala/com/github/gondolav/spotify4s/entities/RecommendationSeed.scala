package com.github.gondolav.spotify4s.entities

import upickle.default._

case class RecommendationSeed(
                               afterFilteringSize: Int,
                               afterRelinkingSize: Int,
                               href: String,
                               id: String,
                               initialPoolSize: Int,
                               `type`: String
                             )

object RecommendationSeed {
  private[spotify4s] implicit val rw: ReadWriter[RecommendationSeed] = macroRW
}