package com.github.gondola.spotify4s.entities

import upickle.default._

case class Album() // TODO
object Album {
  implicit val rw: ReadWriter[Album] = macroRW
}
