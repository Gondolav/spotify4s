package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
import upickle.default._

case class AlbumJson(album_group: Option[String] = None,
                     album_type: String,
                     artists: List[ArtistJson],
                     available_markets: List[String] = Nil,
                     copyrights: Option[List[CopyrightJson]] = None,
                     external_ids: Option[Map[String, String]] = None,
                     external_urls: Map[String, String],
                     genres: Option[List[String]] = None,
                     href: String,
                     id: String,
                     images: List[Image],
                     label: Option[String] = None,
                     name: String,
                     popularity: Option[Int] = None,
                     release_date: String,
                     release_date_precision: String,
                     restrictions: Option[Restrictions] = None,
                     tracks: Option[Paging[TrackJson]] = None,
                     `type`: String,
                     uri: String)

object AlbumJson {
  implicit val rw: ReadWriter[AlbumJson] = macroRW

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

sealed trait AlbumType

object AlbumType {
  def fromString(s: String): AlbumType = s.toLowerCase match {
    case "album" => AlbumT
    case "single" => Single
    case "compilation" => Compilation
  }
}

case object AlbumT extends AlbumType // to avoid conflicts with Album

case object Single extends AlbumType

case object Compilation extends AlbumType

case class Album(albumGroup: Option[String] = None,
                 albumType: AlbumType,
                 artists: List[Artist],
                 availableMarkets: List[String] = Nil,
                 copyrights: Option[List[Copyright]] = None,
                 externalIds: Option[Map[String, String]] = None,
                 externalUrls: Map[String, String],
                 genres: Option[List[String]] = None,
                 href: String,
                 id: String,
                 images: List[Image],
                 label: Option[String] = None,
                 name: String,
                 popularity: Option[Int] = None,
                 releaseDate: String,
                 releaseDatePrecision: ReleaseDatePrecision,
                 restrictions: Option[Restrictions] = None,
                 tracks: Option[Paging[Track]] = None,
                 objectType: String,
                 uri: URI)

object Album {
  def fromJson(json: AlbumJson): Album =
    Album(
      json.album_group,
      AlbumType.fromString(json.album_type),
      json.artists.map(Artist.fromJson),
      json.available_markets,
      json.copyrights.map(copyrights => copyrights.map(Copyright.fromJson)),
      json.external_ids,
      json.external_urls,
      json.genres,
      json.href,
      json.id,
      json.images,
      json.label,
      json.name,
      json.popularity,
      json.release_date,
      ReleaseDatePrecision.fromString(json.release_date_precision),
      json.restrictions,
      json.tracks.map(tracks => tracks.copy(items = tracks.items.map(tracks => tracks.map(Track.fromJson)))),
      json.`type`,
      URI.create(json.uri))
}
