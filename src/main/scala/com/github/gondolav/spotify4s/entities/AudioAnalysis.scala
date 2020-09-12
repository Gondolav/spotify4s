package com.github.gondolav.spotify4s.entities

import upickle.default._

private[spotify4s] case class AudioAnalysisJson(
                                                 bars: List[TimeInterval],
                                                 beats: List[TimeInterval],
                                                 sections: List[SectionJson],
                                                 segments: List[SegmentJson],
                                                 tatums: List[TimeInterval]
                                               )

private[spotify4s] object AudioAnalysisJson {
  implicit val rw: ReadWriter[AudioAnalysisJson] = macroRW
}

case class AudioAnalysis(
                          bars: List[TimeInterval],
                          beats: List[TimeInterval],
                          sections: List[Section],
                          segments: List[Segment],
                          tatums: List[TimeInterval]
                        )

object AudioAnalysis {
  private[spotify4s] def fromJson(json: AudioAnalysisJson): AudioAnalysis = AudioAnalysis(
    json.bars,
    json.beats,
    json.sections.map(Section.fromJson),
    json.segments.map(Segment.fromJson),
    json.tatums
  )
}

case class TimeInterval(start: Double, duration: Double, confidence: Double)

object TimeInterval {
  implicit val rw: ReadWriter[TimeInterval] = macroRW
}

private[spotify4s] case class SectionJson(
                                           start: Double,
                                           duration: Double,
                                           confidence: Double,
                                           loudness: Double,
                                           tempo: Double,
                                           tempo_confidence: Double,
                                           key: Int,
                                           key_confidence: Double,
                                           mode: Int,
                                           mode_confidence: Double,
                                           time_signature: Int,
                                           time_signature_confidence: Double
                                         )

private[spotify4s] object SectionJson {
  implicit val rw: ReadWriter[SectionJson] = macroRW
}

case class Section(
                    start: Double,
                    duration: Double,
                    confidence: Double,
                    loudness: Double,
                    tempo: Double,
                    tempoConfidence: Double,
                    key: Int,
                    keyConfidence: Double,
                    mode: Int,
                    modeConfidence: Double,
                    timeSignature: Int,
                    timeSignatureConfidence: Double
                  )

object Section {
  private[spotify4s] def fromJson(json: SectionJson): Section = Section(
    json.start,
    json.duration,
    json.confidence,
    json.loudness,
    json.tempo,
    json.tempo_confidence,
    json.key,
    json.key_confidence,
    json.mode,
    json.mode_confidence,
    json.time_signature,
    json.time_signature_confidence
  )
}

private[spotify4s] case class SegmentJson(
                                           start: Double,
                                           duration: Double,
                                           confidence: Double,
                                           loudness_start: Double,
                                           loudness_max: Double,
                                           loudness_max_time: Double,
                                           loudness_end: Double,
                                           pitches: List[Double],
                                           timbre: List[Double]
                                         )

private[spotify4s] object SegmentJson {
  implicit val rw: ReadWriter[SegmentJson] = macroRW
}

case class Segment(
                    start: Double,
                    duration: Double,
                    confidence: Double,
                    loudnessStart: Double,
                    loudnessMax: Double,
                    loudnessMaxTime: Double,
                    loudnessEnd: Double,
                    pitches: List[Double],
                    timbre: List[Double]
                  )

object Segment {
  private[spotify4s] def fromJson(json: SegmentJson): Segment = Segment(
    json.start,
    json.duration,
    json.confidence,
    json.loudness_start,
    json.loudness_max,
    json.loudness_max_time,
    json.loudness_end,
    json.pitches,
    json.timbre
  )
}