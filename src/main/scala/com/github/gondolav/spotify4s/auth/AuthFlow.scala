package com.github.gondolav.spotify4s.auth

import java.net.URI
import java.util.Base64

import requests.RequestFailedException
import upickle.default._

sealed trait AuthFlow {
  def authenticate: Either[AuthError, AuthObj]
}

case class AuthCode(clientID: String, redirectURI: URI, scopes: List[String]) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = ???
} // TODO

case class AuthCodeWithPKCE(clientID: String, redirectURI: URI, scopes: List[String]) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = ???
} // TODO

case class ClientCredentials(clientID: String, clientSecret: String) extends AuthFlow {
  private val endpoint = "https://accounts.spotify.com/api/token"

  override def authenticate: Either[AuthError, AuthObj] = {
    val encodedAuth = Base64.getEncoder.encodeToString(f"$clientID:$clientSecret".getBytes)

    try {
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")), data = Map("grant_type" -> "client_credentials"))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }
}

