package com.github.gondolav.spotify4s.entities

sealed trait ReleaseDatePrecision

object ReleaseDatePrecision {
  def fromString(s: String): ReleaseDatePrecision = s match {
    case "year" => Year
    case "month" => Month
    case "day" => Day
  }
}

case object Year extends ReleaseDatePrecision

case object Month extends ReleaseDatePrecision

case object Day extends ReleaseDatePrecision
