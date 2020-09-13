package com.github.gondolav.spotify4s.auth

import java.net.URI
import java.util.Base64

import requests.RequestFailedException
import upickle.default._

import scala.io.StdIn.readLine
import scala.util.Random

/**
 * An [[https://developer.spotify.com/documentation/general/guides/authorization-guide/ authorization flow]].
 */
sealed trait AuthFlow {
  protected val endpoint = "https://accounts.spotify.com/api/token"

  private[spotify4s] def authenticate: Either[AuthError, AuthObj]

  private[spotify4s] def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj]
}

/**
 * The [[https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow Authorization Code Flow]].
 *
 * This flow is suitable for long-running applications in which the user grants permission only once. It provides an
 * access token that can be refreshed. Since the token exchange involves sending your secret key, perform this on a
 * secure location, like a backend service, and not from a client such as a browser or from a mobile app.
 *
 * Since the exchange uses your client secret key, to keep the integrity of the key, you should make that request server-side.
 *
 * The advantage of this flow is that you can use refresh tokens to extend the validity of the access token.
 *
 */
case class AuthCode(clientID: String, clientSecret: String, redirectURI: URI, scopes: List[String] = Nil) extends AuthFlow {
  private val authorizeEndpoint = "https://accounts.spotify.com/authorize"
  private val encodedAuth = Base64.getEncoder.encodeToString(f"$clientID:$clientSecret".getBytes)

  override private[spotify4s] def authenticate: Either[AuthError, AuthObj] = {
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

  override private[spotify4s] def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] = {
    try {
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")),
        data = Map("grant_type" -> "refresh_token", "refresh_token" -> refreshToken))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }
}

/**
 * The [[https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow-with-proof-key-for-code-exchange-pkce Authorization Code Flow with Proof Key for Code Exchange (PKCE)]].
 *
 * The authorization code flow with PKCE is the best option for mobile and desktop applications where it is unsafe to
 * store your client secret. It provides your app with an access token that can be refreshed.
 */
case class AuthCodeWithPKCE(clientID: String, clientSecret: String, redirectURI: URI, scopes: List[String]) extends AuthFlow {
  override private[spotify4s] def authenticate: Either[AuthError, AuthObj] = ???

  override private[spotify4s] def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] = {
    try {
      val req = requests.post(endpoint,
        data = Map("grant_type" -> "refresh_token", "refresh_token" -> refreshToken, "client_id" -> clientID))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }
} // TODO

/**
 * The [[https://developer.spotify.com/documentation/general/guides/authorization-guide/#client-credentials-flow Client Credentials Flow]].
 *
 * The Client Credentials flow is used in server-to-server authentication. Only endpoints that do not access user
 * information can be accessed. The advantage here in comparison with requests to the Web API made without an access
 * token, is that a higher rate limit is applied.
 *
 * This flow makes it possible to authenticate your requests to the Spotify Web API and to obtain a higher rate limit
 * than you would get without authentication.
 *
 * Note: however that this flow does not include authorization and therefore cannot be used to access or to manage a
 * user private data.
 */
case class ClientCredentials(clientID: String, clientSecret: String) extends AuthFlow {
  override private[spotify4s] def authenticate: Either[AuthError, AuthObj] = {
    try {
      val encodedAuth = Base64.getEncoder.encodeToString(f"$clientID:$clientSecret".getBytes)
      val req = requests.post(endpoint, headers = List(("Authorization", f"Basic $encodedAuth")), data = Map("grant_type" -> "client_credentials"))
      Right(AuthObj.fromJson(read[AuthObjJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(AuthError.fromJson(read[AuthErrorJson](e.response.text)))
    }
  }

  override private[spotify4s] def requestRefreshedToken(refreshToken: String): Either[AuthError, AuthObj] =
    Left(AuthError("Cannot refresh token", "Cannot refresh token with client credentials flow"))
}

