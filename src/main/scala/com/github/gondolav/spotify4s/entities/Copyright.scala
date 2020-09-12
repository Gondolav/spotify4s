package com.github.gondolav.spotify4s.entities

import upickle.default._

private[spotify4s] case class CopyrightJson(text: String, `type`: String)

private[spotify4s] object CopyrightJson {
  implicit val rw: ReadWriter[CopyrightJson] = macroRW
}

sealed trait CopyrightType

object CopyrightType {
  def fromString(s: String): CopyrightType = s match {
    case "C" => C
    case "P" => P
  }
}

case object C extends CopyrightType

case object P extends CopyrightType

case class Copyright(text: String, copyrightType: CopyrightType)

object Copyright {
  private[spotify4s] def fromJson(json: CopyrightJson): Copyright = Copyright(json.text, CopyrightType.fromString(json.`type`))
}
