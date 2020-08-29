package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

case class LinkedTrackJson(external_urls: Map[String, String], href: String, id: String, `type`: String, uri: String)

object LinkedTrackJson {
  implicit val rw: ReadWriter[LinkedTrackJson] = macroRW
}

case class LinkedTrack(externalUrls: Map[String, String], href: String, id: String, objectType: String, uri: URI)

object LinkedTrack {
  def fromJson(json: LinkedTrackJson): LinkedTrack = LinkedTrack(
    json.external_urls,
    json.href,
    json.id,
    json.`type`,
    URI.create(json.uri)
  )
}
