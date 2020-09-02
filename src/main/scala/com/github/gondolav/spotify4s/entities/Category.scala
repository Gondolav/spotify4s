package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Category(href: String, icons: List[Image], id: String, name: String)
object Category {
  implicit val rw: ReadWriter[Category] = macroRW
}