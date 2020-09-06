package com.github.gondolav.spotify4s.entities

sealed trait ObjectType

object ObjectType {
  def fromString(s: String): ObjectType = s.toLowerCase match {
    case "album" => AlbumObj
    case "artist" => ArtistObj
    case "episode" => EpisodeObj
    case "show" => ShowObj
    case "track" => TrackObj
  }
}

case object AlbumObj extends ObjectType

case object ArtistObj extends ObjectType

case object EpisodeObj extends ObjectType

case object ShowObj extends ObjectType

case object TrackObj extends ObjectType
