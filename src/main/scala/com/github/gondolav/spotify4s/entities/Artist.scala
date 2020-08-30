package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
import upickle.default._

case class ArtistJson(
                       external_urls: Map[String, String],
                       followers: Option[Followers] = None,
                       genres: Option[List[String]] = None,
                       href: String,
                       id: String,
                       images: Option[List[Image]] = None,
                       name: String,
                       popularity: Option[Int] = None,
                       `type`: String,
                       uri: String
                     )

object ArtistJson {
  implicit val rw: ReadWriter[ArtistJson] = macroRW

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

case class Artist(
                   externalUrls: Map[String, String],
                   followers: Option[Followers],
                   genres: Option[List[String]],
                   href: String,
                   id: String,
                   images: Option[List[Image]],
                   name: String,
                   popularity: Option[Int],
                   objectType: String,
                   uri: URI
                 )

object Artist {
  def fromJson(json: ArtistJson): Artist = Artist(
    json.external_urls,
    json.followers,
    json.genres,
    json.href,
    json.id,
    json.images,
    json.name,
    json.popularity,
    json.`type`,
    URI.create(json.uri)
  )
}
