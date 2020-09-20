package com.github.gondolav.spotify4s.auth

import upickle.default._

private case class AuthErrorJson(error: String, error_description: Option[String] = None)

private object AuthErrorJson {
  implicit val rw: ReadWriter[AuthErrorJson] = macroRW
}

/**
 * Stores the error response returned whenever the client makes requests related to authorization. It follows
 * [[https://tools.ietf.org/html/rfc6749 RFC 6749]] on the OAuth 2.0 Authorization Framework.
 *
 * @param error            a high level description of the error as specified in
 *                         [[https://tools.ietf.org/html/rfc6749#section-5.2 RFC 6749 Section 5.2]]
 * @param errorDescription (optional) a more detailed description of the error as specified in
 *                         [[https://tools.ietf.org/html/rfc6749#section-4.1.2.1 RFC 6749 Section 4.1.2.1]]
 */
case class AuthError(error: String, errorDescription: Option[String] = None)

object AuthError {
  private[spotify4s] def fromJson(json: AuthErrorJson): AuthError = AuthError(json.error, json.error_description)
}
