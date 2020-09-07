package com.github.gondolav.spotify4s.auth

import java.net.URI
import java.util.Base64

import requests.RequestFailedException
import upickle.default._

import scala.io.StdIn.readLine
import scala.util.Random

sealed trait AuthFlow {
  protected val endpoint = "https://accounts.spotify.com/api/token"

  def authenticate: Either[AuthError, AuthObj]

  def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj]
}

case class AuthCode(clientID: String, clientSecret: String, redirectURI: URI, scopes: List[String] = Nil) extends AuthFlow {
  private val authorizeEndpoint = "https://accounts.spotify.com/authorize"
  private val encodedAuth = Base64.getEncoder.encodeToString(f"$clientID:$clientSecret".getBytes)

  override def authenticate: Either[AuthError, AuthObj] = {
    val state = Random.alphanumeric.take(10).mkString
    val codeReq = requests.get(authorizeEndpoint,
      params = List(("client_id", clientID), ("response_type", "code"), ("redirect_uri", redirectURI.toString),
        ("state", state)) ++ (if (scopes.nonEmpty) List(("scope", scopes.mkString(" "))) else Nil))

    println(f"Visit this link to authenticate: ${codeReq.url}")
    val url = readLine("Enter the URL you were redirected to: ")
    val res = url.split('?')(1).split('&')
    val (codeKey, code) = {
      val split = res(0).split('=')
      (split(0), split(1))
    }
    val stateReceived = {
      val split = res(1).split('=')
      split(1)
    }

    if (state != stateReceived) return Left(AuthError("Wrong state", f"Wrong state: expected $state, received $stateReceived"))
    if (codeKey != "code") return Left(AuthError("Error while authenticating", f"Reason: $code"))

    try {
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")),
        data = Map("grant_type" -> "authorization_code", "code" -> code, "redirect_uri" -> redirectURI.toString))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }

  override def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] = {
    try {
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")),
        data = Map("grant_type" -> "refresh_token", "refresh_token" -> refreshToken))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }
}

case class AuthCodeWithPKCE(clientID: String, clientSecret: String, redirectURI: URI, scopes: List[String]) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = ???

  override def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] = {
    try {
      val req = requests.post(endpoint,
        data = Map("grant_type" -> "refresh_token", "refresh_token" -> refreshToken, "client_id" -> clientID))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }
} // TODO

case class ClientCredentials(clientID: String, clientSecret: String) extends AuthFlow {
  override def authenticate: Either[AuthError, AuthObj] = {
    try {
      val encodedAuth = Base64.getEncoder.encodeToString(f"$clientID:$clientSecret".getBytes)
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")), data = Map("grant_type" -> "client_credentials"))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }

  override def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] =
    Left(AuthError("Cannot refresh token", "Cannot refresh token with client credentials flow"))
}

