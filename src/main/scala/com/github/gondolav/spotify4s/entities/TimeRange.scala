package com.github.gondolav.spotify4s.entities

sealed trait TimeRange

case object LongTerm extends TimeRange {
  override def toString: String = "long_term"
}

case object MediumTerm extends TimeRange {
  override def toString: String = "medium_term"
}

case object ShortTerm extends TimeRange {
  override def toString: String = "short_term"
}
