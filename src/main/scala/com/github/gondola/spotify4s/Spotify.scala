package com.github.gondola.spotify4s

import com.github.gondola.spotify4s.auth.{AuthCode, AuthCodeWithPKCE, AuthError, AuthFlow, AuthObj, ClientCredentials}
import com.github.gondola.spotify4s.entities.Album

class Spotify(private val authFlow: AuthFlow) {
  private val endpoint = "https://api.spotify.com/v1/"

    val authObj: Either[AuthError, AuthObj] = authFlow.authenticate

  def getAlbum(id: String): Either[Error, Album] = ???
}

object Spotify {
  def apply(authFlow: AuthFlow): Spotify = new Spotify(authFlow)
}


