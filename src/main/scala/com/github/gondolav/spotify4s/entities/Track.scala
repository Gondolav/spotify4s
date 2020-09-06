package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
import upickle.default._

case class TrackJson(
                      album: Option[AlbumJson] = None,
                      artists: List[ArtistJson],
                      available_markets: List[String] = Nil,
                      disc_number: Int,
                      duration_ms: Int,
                      explicit: Boolean,
                      external_ids: Option[Map[String, String]] = None,
                      external_urls: Map[String, String],
                      href: String,
                      id: String,
                      is_playable: Option[Boolean] = None,
                      linked_from: Option[LinkedTrackJson] = None,
                      restrictions: Option[Restrictions] = None,
                      name: String,
                      popularity: Option[Int] = None,
                      preview_url: String,
                      track_number: Int,
                      `type`: String,
                      uri: String,
                      is_local: Boolean
                    )

object TrackJson {
  implicit val rw: ReadWriter[TrackJson] = macroRW

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

case class Track(
                  album: Option[Album] = None,
                  artists: List[Artist],
                  availableMarkets: List[String] = Nil,
                  discNumber: Int,
                  durationMs: Int,
                  explicit: Boolean,
                  externalIds: Option[Map[String, String]] = None,
                  externalUrls: Map[String, String],
                  href: String,
                  id: String,
                  isPlayable: Option[Boolean] = None,
                  linkedFrom: Option[LinkedTrack] = None,
                  restrictions: Option[Restrictions] = None,
                  name: String,
                  popularity: Option[Int] = None,
                  previewUrl: String,
                  trackNumber: Int,
                  objectType: ObjectType = TrackObj,
                  uri: URI,
                  isLocal: Boolean
                )

object Track {
  def fromJson(json: TrackJson): Track = Track(
    json.album.map(album => Album.fromJson(album)),
    json.artists.map(Artist.fromJson),
    json.available_markets,
    json.disc_number,
    json.duration_ms,
    json.explicit,
    json.external_ids,
    json.external_urls,
    json.href,
    json.id,
    json.is_playable,
    json.linked_from.map(linkedTrack => LinkedTrack.fromJson(linkedTrack)),
    json.restrictions,
    json.name,
    json.popularity,
    json.preview_url,
    json.track_number,
    ObjectType.fromString(json.`type`),
    URI.create(json.uri),
    json.is_local
  )
}
