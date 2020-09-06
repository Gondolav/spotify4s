package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
import upickle.default._

case class EpisodeJson(
                        audio_preview_url: String,
                        description: String,
                        duration_ms: Int,
                        explicit: Boolean,
                        external_urls: Map[String, String],
                        href: String,
                        id: String,
                        images: List[Image],
                        is_externally_hosted: Boolean,
                        is_playable: Boolean,
                        languages: List[String],
                        name: String,
                        release_date: String,
                        release_date_precision: String,
                        resume_point: Option[ResumePointJson] = None,
                        show: Option[ShowJson] = None,
                        `type`: String,
                        uri: String
                      )

object EpisodeJson {
  implicit val rw: ReadWriter[EpisodeJson] = macroRW

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

case class Episode(
                    audioPreviewUrl: String,
                    description: String,
                    durationMs: Int,
                    explicit: Boolean,
                    externalUrls: Map[String, String],
                    href: String,
                    id: String,
                    images: List[Image],
                    isExternallyHosted: Boolean,
                    isPlayable: Boolean,
                    languages: List[String],
                    name: String,
                    releaseDate: String,
                    releaseDatePrecision: ReleaseDatePrecision,
                    resumePoint: Option[ResumePoint] = None,
                    show: Option[Show] = None,
                    objectType: ObjectType = EpisodeObj,
                    uri: URI
                  ) extends Searchable

object Episode {
  def fromJson(json: EpisodeJson): Episode = if (json == null) null else
    Episode(
      json.audio_preview_url,
      json.description,
      json.duration_ms,
      json.explicit,
      json.external_urls,
      json.href,
      json.id,
      json.images,
      json.is_externally_hosted,
      json.is_playable,
      json.languages,
      json.name,
      json.release_date,
      ReleaseDatePrecision.fromString(json.release_date_precision),
      json.resume_point.map(ResumePoint.fromJson),
      json.show.map(Show.fromJson),
      ObjectType.fromString(json.`type`),
      URI.create(json.uri)
    )
}