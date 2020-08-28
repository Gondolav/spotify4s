package com.github.gondola.spotify4s.auth

import java.net.URI
import java.util.Base64
import upickle.default._

sealed trait AuthFlow {
  def authenticate: Either[AuthError, AuthObj]
}

case class AuthCode(clientId: String, redirectUri: URI, scopes: List[String]) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = ???
} // TODO

case class AuthCodeWithPKCE(clientId: String, redirectUri: URI, scopes: List[String]) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = ???
} // TODO

case class ClientCredentials(clientId: String, clientSecret: String) extends AuthFlow {
  private val endpoint = "https://accounts.spotify.com/api/token"

  override def authenticate: Either[AuthError, AuthObj] = {
    val encodedAuth = Base64.getEncoder.encodeToString(f"$clientId:$clientSecret".getBytes)

    val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")), data = Map("grant_type" -> "client_credentials"))
    println(req.text)
    read[Either[AuthErrorJson, AuthObjJson]](req.text).fold(
      errorJson => Left(AuthError.fromJson(errorJson)),
      objJson => Right(AuthObj.fromJson(objJson))
    )
  }
}

