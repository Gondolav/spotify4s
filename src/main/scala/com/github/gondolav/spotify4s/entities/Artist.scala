package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

case class ArtistJson(
                       external_urls: Map[String, String],
                       followers: Followers,
                       genres: List[String],
                       href: String,
                       id: String,
                       images: List[Image],
                       name: String,
                       popularity: Int,
                       `type`: String,
                       uri: String
                     )

object ArtistJson {
  implicit val rw: ReadWriter[ArtistJson] = macroRW
}

case class Artist(
                   externalUrls: Map[String, String],
                   followers: Followers,
                   genres: List[String],
                   href: String,
                   id: String,
                   images: List[Image],
                   name: String,
                   popularity: Int,
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
