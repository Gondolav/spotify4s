package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Restrictions(reason: String)

object Restrictions {
  private[spotify4s] implicit val rw: ReadWriter[Restrictions] = macroRW
}