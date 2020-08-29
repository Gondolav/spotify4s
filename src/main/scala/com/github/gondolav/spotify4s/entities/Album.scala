package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

case class AlbumJson(album_type: String,
                     artists: List[Artist],
                     available_markets: List[String],
                     copyrights: List[Copyright],
                     external_ids: Map[String, String],
                     external_urls: Map[String, String],
                     genres: List[String],
                     href: String,
                     id: String,
                     images: List[Image],
                     label: String,
                     name: String,
                     popularity: Int,
                     release_date: String,
                     release_date_precision: String,
                     restrictions: Restrictions,
                     tracks: Paging[Track],
                     `type`: String,
                     uri: String)

object AlbumJson {
  implicit val rw: ReadWriter[AlbumJson] = macroRW
}

sealed trait AlbumType

object AlbumType {
  def fromString(s: String): AlbumType = s match {
    case "album" => AlbumT
    case "single" => Single
    case "compilation" => Compilation
  }
}

case object AlbumT extends AlbumType // to avoid conflicts with Album

case object Single extends AlbumType

case object Compilation extends AlbumType

sealed trait ReleaseDatePrecision

object ReleaseDatePrecision {
  def fromString(s: String): ReleaseDatePrecision = s match {
    case "year" => Year
    case "month" => Month
    case "day" => Day
  }
}

case object Year extends ReleaseDatePrecision

case object Month extends ReleaseDatePrecision

case object Day extends ReleaseDatePrecision

case class Album(albumType: AlbumType,
                 artists: List[Artist],
                 availableMarkets: List[String],
                 copyrights: List[Copyright],
                 externalIds: Map[String, String],
                 externalUrls: Map[String, String],
                 genres: List[String],
                 href: String,
                 id: String,
                 images: List[Image],
                 label: String,
                 name: String,
                 popularity: Int,
                 releaseDate: String,
                 releaseDatePrecision: ReleaseDatePrecision,
                 restrictions: Restrictions,
                 tracks: Paging[Track],
                 objectType: String,
                 uri: URI)

object Album {
  def fromJson(json: AlbumJson): Album =
    Album(AlbumType.fromString(json.album_type),
      json.artists,
      json.available_markets,
      json.copyrights,
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
      json.tracks,
      json.`type`,
      URI.create(json.uri))
}
