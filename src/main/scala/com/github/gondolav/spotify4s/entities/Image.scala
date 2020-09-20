package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Image(width: Int, height: Int, url: String)

object Image {
  private[spotify4s] implicit val rw: ReadWriter[Image] = macroRW
}
