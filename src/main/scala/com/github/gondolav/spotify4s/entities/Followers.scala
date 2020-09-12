package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Followers(href: String, total: Int)

object Followers {
  private[spotify4s] implicit val rw: ReadWriter[Followers] = macroRW
}