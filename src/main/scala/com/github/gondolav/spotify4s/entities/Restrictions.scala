package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Restrictions(reason: String)
object Restrictions {
  implicit val rw: ReadWriter[Restrictions] = macroRW
}