package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
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

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
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
                 objectType: ObjectType = ShowObj,
                 uri: URI
               ) extends Searchable

object Show {
  def fromJson(json: ShowJson): Show = if (json == null) null else
    Show(
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
      ObjectType.fromString(json.`type`),
      URI.create(json.uri)
    )
}
