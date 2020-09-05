package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

case class ShowJson(
                     available_markets: List[String],
                     copyrights: List[CopyrightJson],
                     description: String,
                     explicit: Boolean,
                     episodes: Option[Paging[EpisodeJson]] = None,
                     external_urls: Map[String, String],
                     href: String,
                     id: String,
                     images: List[Image],
                     is_externally_hosted: Boolean,
                     languages: List[String],
                     media_type: String,
                     name: String,
                     publisher: String,
                     `type`: String,
                     uri: String
                   )

object ShowJson {
  implicit val rw: ReadWriter[ShowJson] = macroRW
}

case class Show(
                 availableMarkets: List[String],
                 copyrights: List[Copyright],
                 description: String,
                 explicit: Boolean,
                 episodes: Option[Paging[Episode]] = None,
                 externalUrls: Map[String, String],
                 href: String,
                 id: String,
                 images: List[Image],
                 isExternallyHosted: Boolean,
                 languages: List[String],
                 mediaType: String,
                 name: String,
                 publisher: String,
                 objectType: String,
                 uri: URI
               )

object Show {
  def fromJson(json: ShowJson): Show = Show(
    json.available_markets,
    json.copyrights.map(Copyright.fromJson),
    json.description,
    json.explicit,
    json.episodes.map(eps => eps.copy(items = eps.items.map(episodes => episodes.map(Episode.fromJson)))),
    json.external_urls,
    json.href,
    json.id,
    json.images,
    json.is_externally_hosted,
    json.languages,
    json.media_type,
    json.name,
    json.publisher,
    json.`type`,
    URI.create(json.uri)
  )
}