package com.github.gondolav.spotify4s.entities

import upickle.default._

case class PlaylistTrackJson(
                              added_at: String,
                              added_by: UserJson,
                              is_local: Boolean,
                              track: TrackJson
                            )

object PlaylistTrackJson {
  implicit val rw: ReadWriter[PlaylistTrackJson] = macroRW
}

case class PlaylistTrack(
                          addedAt: String,
                          addedBy: User,
                          isLocal: Boolean,
                          track: Track
                        )

object PlaylistTrack {
  def fromJson(json: PlaylistTrackJson): PlaylistTrack = PlaylistTrack(
    json.added_at,
    User.fromJson(json.added_by),
    json.is_local,
    Track.fromJson(json.track)
  )
}

