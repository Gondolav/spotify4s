package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.{AuthException, AuthFlow, AuthObj}
import com.github.gondolav.spotify4s.entities.{Album, AlbumJson, Category, Error, Paging, Playlist, PlaylistJson, Track, TrackJson}
import requests.RequestFailedException
import upickle.default._

class Spotify(authFlow: AuthFlow) {
  private val endpoint = "https://api.spotify.com/v1"

  private val authObj: AuthObj = authFlow.authenticate match {
    case Left(error) => throw new AuthException(f"An error occurred while authenticating: '${error.errorDescription}'\n", error)
    case Right(value) => value
  }

  private def withErrorHandling[T](task: => Right[Nothing, T]): Either[Error, T] = {
    try {
      task
    } catch {
      case e: RequestFailedException => Left(read[Error](e.response.text))
    }
  }

  /**
   * Gets Spotify catalog information for a single album.
   *
   * @param id     the Spotify ID for the album
   * @param market (optional) an ISO 3166-1 alpha-2 country code or the string from_token. Provide this parameter if you want to apply Track Relinking
   * @return an [[Album]] on success, otherwise it returns [[Error]]
   */
  def getAlbum(id: String, market: String = ""): Either[Error, Album] = withErrorHandling {
    val req = requests.get(f"$endpoint/albums/$id", headers = List(("Authorization", f"Bearer ${authObj.accessToken}")), params = if (market.nonEmpty) List(("market", market)) else Nil)
    Right(Album.fromJson(read[AlbumJson](req.text)))
  }

  /**
   * Gets Spotify catalog information about an album’s tracks. Optional parameters can be used to limit the number of tracks returned.
   *
   * @param id     the Spotify ID for the album
   * @param limit  (optional) the maximum number of tracks to return. Default: 20. Minimum: 1. Maximum: 50
   * @param offset (optional) the index of the first track to return. Default: 0 (the first object). Use with limit to get the next set of tracks
   * @param market (optional) an ISO 3166-1 alpha-2 country code or the string from_token. Provide this parameter if you want to apply Track Relinking
   * @return a [[Paging]] object wrapping [[Track]]s on success, otherwise it returns [[Error]]
   */
  def getAlbumTracks(id: String, limit: Int = 20, offset: Int = 0, market: String = ""): Either[Error, Paging[Track]] = withErrorHandling {
    require(1 <= limit && limit <= 50, "The limit parameter must be between 1 and 50")
    require(offset >= 0, "The offset parameter must be non-negative")

    val req = requests.get(f"$endpoint/albums/$id/tracks",
      headers = List(("Authorization", f"Bearer ${authObj.accessToken}")),
      params = List(("limit", limit.toString), ("offset", offset.toString)) ++ (if (market.nonEmpty) List(("market", market)) else Nil))

    val res = read[Paging[TrackJson]](req.text)
    Right(res.copy(items = res.items.map(Track.fromJson)))
  }

  /**
   * Gets Spotify catalog information for multiple albums identified by their Spotify IDs.
   *
   * Objects are returned in the order requested. If an object is not found, a null value is returned in the
   * appropriate position. Duplicate ids in the query will result in duplicate objects in the response.
   *
   * @param ids    a list containing the Spotify IDs for the albums. Maximum: 20 IDs
   * @param market (optional) an ISO 3166-1 alpha-2 country code or the string from_token. Provide this parameter if you want to apply Track Relinking
   * @return a List of [[Album]]s on success, otherwise it returns [[Error]]
   */
  def getAlbums(ids: List[String], market: String = ""): Either[Error, List[Album]] = withErrorHandling {
    require(ids.length <= 20, "The maximum number of IDs is 20")

    val req = requests.get(f"$endpoint/albums",
      headers = List(("Authorization", f"Bearer ${authObj.accessToken}")),
      params = List(("ids", ids.mkString(","))) ++ (if (market.nonEmpty) List(("market", market)) else Nil))

    val res = read[Map[String, List[AlbumJson]]](req.text)
    Right(res("albums").map(Album.fromJson))
  }

  /**
   * Gets a single category used to tag items in Spotify (on, for example, the Spotify player’s “Browse” tab).
   *
   * @param id      the Spotify category ID for the category
   * @param country (optional) a country: an ISO 3166-1 alpha-2 country code. Provide this parameter to ensure that the category exists for a particular country
   * @param locale  (optional) the desired language, consisting of an ISO 639-1 language code and an ISO 3166-1 alpha-2
   *                country code, joined by an underscore. For example: es_MX, meaning "Spanish (Mexico)".
   *                Provide this parameter if you want the category strings returned in a particular language.
   *                Note that, if locale is not supplied, or if the specified language is not available,
   *                the category strings returned will be in the Spotify default language (American English)
   * @return a [[Category]] on success, otherwise it returns [[Error]]
   */
  def getCategory(id: String, country: String = "", locale: String = ""): Either[Error, Category] = {
    val req = requests.get(f"$endpoint/browse/categories/$id",
      headers = List(("Authorization", f"Bearer ${authObj.accessToken}")),
      params = (if (country.nonEmpty) List(("country", country)) else Nil) ++ (if (locale.nonEmpty) List(("locale", locale)) else Nil))

    Right(read[Category](req.text))
  }

  /**
   * Gets a list of Spotify playlists tagged with a particular category.
   *
   * @param id      the Spotify category ID for the category
   * @param country (optional) a country: an ISO 3166-1 alpha-2 country code
   * @param limit   (optional) the maximum number of items to return. Default: 20. Minimum: 1. Maximum: 50
   * @param offset  (optional) the index of the first item to return. Default: 0 (the first object).
   *                Use with limit to get the next set of items
   * @return a [[Paging]] object wrapping [[Playlist]]s on success, otherwise it returns [[Error]]
   */
  def getCategoryPlaylists(id: String, country: String = "", limit: Int = 20, offset: Int = 0): Either[Error, Paging[Playlist]] = {
    require(1 <= limit && limit <= 50, "The limit parameter must be between 1 and 50")
    require(offset >= 0, "The offset parameter must be non-negative")

    val req = requests.get(f"$endpoint/browse/categories/$id/playlists",
      headers = List(("Authorization", f"Bearer ${authObj.accessToken}")),
      params = List(("limit", limit.toString), ("offset", offset.toString)) ++ (if (country.nonEmpty) List(("country", country)) else Nil))

    println(req.text)
    val res = {
      val map = read[Map[String, Paging[PlaylistJson]]](req.text)
      map("playlists")
    }


    Right(res.copy(items = res.items.map(Playlist.fromJson)))
  }
}

object Spotify {
  def apply(authFlow: AuthFlow): Spotify = new Spotify(authFlow)
}


