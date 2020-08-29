package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.{AuthException, AuthFlow, AuthObj}
import com.github.gondolav.spotify4s.entities.{Album, AlbumJson, Error}
import requests.RequestFailedException
import upickle.default._

class Spotify(authFlow: AuthFlow) {
  private val endpoint = "https://api.spotify.com/v1"

  private val authObj: AuthObj = authFlow.authenticate match {
    case Left(error) => throw new AuthException(f"An error occurred while authenticating: '${error.errorDescription}'\n", error)
    case Right(value) => value
  }

  /**
   * Gets Spotify catalog information for a single album.
   *
   * @param id     the Spotify ID for the album
   * @param market (optional) an ISO 3166-1 alpha-2 country code or the string from_token. Provide this parameter if you want to apply Track Relinking
   * @return an [[Album]] on success, otherwise it returns [[Error]]
   */
  def getAlbum(id: String, market: String = ""): Either[Error, Album] = {
    try {
      val req = requests.get(f"$endpoint/albums/$id", headers = List(("Authorization", f"Bearer ${authObj.accessToken}")), params = if (market.nonEmpty) List(("market", market)) else Nil)
      Right(Album.fromJson(read[AlbumJson](req.text)))
    } catch {
      case e: RequestFailedException => Left(read[Error](e.response.text))
    }
  }
}

object Spotify {
  def apply(authFlow: AuthFlow): Spotify = new Spotify(authFlow)
}


