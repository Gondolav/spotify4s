package com.github.gondolav.spotify4s.entities

import upickle.default._

case class ResumePointJson(fully_played: Boolean, resume_position_ms: Int)

object ResumePointJson {
  implicit val rw: ReadWriter[ResumePointJson] = macroRW
}

case class ResumePoint(fullyPlayed: Boolean, resumePositionMs: Int)

object ResumePoint {
  def fromJson(json: ResumePointJson): ResumePoint = ResumePoint(json.fully_played, json.resume_position_ms)
}