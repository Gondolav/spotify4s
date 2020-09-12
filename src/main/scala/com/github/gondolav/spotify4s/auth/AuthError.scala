package com.github.gondolav.spotify4s.auth

import upickle.default._

private[spotify4s] case class AuthErrorJson(error: String, error_description: String)

private[spotify4s] object AuthErrorJson {
  implicit val rw: ReadWriter[AuthErrorJson] = macroRW
}

case class AuthError(error: String, errorDescription: String)

object AuthError {
  private[spotify4s] def fromJson(json: AuthErrorJson): AuthError = AuthError(json.error, json.error_description)
}
