package com.github.gondola.spotify4s.auth

import upickle.default._

case class AuthErrorJson(error: String, error_description: String)
object AuthErrorJson {
  implicit val rw: ReadWriter[AuthErrorJson] = macroRW
}

case class AuthError(error: String, errorDescription: String)
object AuthError {
  def fromJson(json: AuthErrorJson): AuthError = AuthError(json.error, json.error_description)
}
