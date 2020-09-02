package com.github.gondolav.spotify4s.entities

import java.net.URI

import upickle.default._

case class PlaylistJson(
                         collaborative: Boolean,
                         description: String,
                         external_urls: Map[String, String],
                         followers: Option[Followers] = None,
                         href: String,
                         id: String,
                         images: List[Image],
                         name: String,
                         owner: UserJson,
                         public: Boolean,
                         snapshot_id: String,
                         tracks: Paging[PlaylistTrackJson],
                         `type`: String,
                         uri: String
                       )
object PlaylistJson {
  implicit val rw: ReadWriter[PlaylistJson] = macroRW
}

case class Playlist(
                     collaborative: Boolean,
                     description: String,
                     externalUrls: Map[String, String],
                     followers: Option[Followers] = None,
                     href: String,
                     id: String,
                     images: List[Image],
                     name: String,
                     owner: User,
                     public: Boolean,
                     snapshotID: String,
                     tracks: Paging[PlaylistTrack],
                     objectType: String,
                     uri: URI
                   )
object Playlist {
  def fromJson(json: PlaylistJson): Playlist = Playlist(
    json.collaborative,
    json.description,
    json.external_urls,
    json.followers,
    json.href,
    json.id,
    json.images,
    json.name,
    User.fromJson(json.owner),
    json.public,
    json.snapshot_id,
    json.tracks.copy(items = json.tracks.items.map(PlaylistTrack.fromJson)),
    json.`type`,
    URI.create(json.uri)
  )
}