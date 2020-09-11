package com.github.gondolav.spotify4s.entities

import java.net.URI

import ujson.{Null, Value}
import upickle.default._

case class UserJson(
                     country: Option[String] = None,
                     display_name: Option[String] = None,
                     email: Option[String] = None,
                     external_urls: Map[String, String],
                     followers: Option[Followers] = None,
                     href: String,
                     id: String,
                     images: Option[List[Image]] = None,
                     product: Option[String] = None,
                     `type`: String,
                     uri: String
                   )

object UserJson {
  implicit val rw: ReadWriter[UserJson] = macroRW

  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

case class User(
                 country: Option[String] = None,
                 displayName: Option[String] = None,
                 email: Option[String] = None,
                 externalUrls: Map[String, String],
                 followers: Option[Followers] = None,
                 href: String,
                 id: String,
                 images: Option[List[Image]] = None,
                 product: Option[String] = None,
                 objectType: String,
                 uri: URI
               )

object User {
  def fromJson(json: UserJson): User = User(
    json.country,
    json.display_name,
    json.email,
    json.external_urls,
    json.followers,
    json.href,
    json.id,
    json.images,
    json.product,
    json.`type`,
    URI.create(json.uri)
  )
}
