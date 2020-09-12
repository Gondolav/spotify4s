package com.github.gondolav.spotify4s.entities

import upickle.default._

private[spotify4s] case class ResumePointJson(fully_played: Boolean, resume_position_ms: Int)

private[spotify4s] object ResumePointJson {
  implicit val rw: ReadWriter[ResumePointJson] = macroRW
}

case class ResumePoint(fullyPlayed: Boolean, resumePositionMs: Int)

object ResumePoint {
  private[spotify4s] def fromJson(json: ResumePointJson): ResumePoint = ResumePoint(json.fully_played, json.resume_position_ms)
}