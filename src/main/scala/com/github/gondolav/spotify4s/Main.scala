package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.ClientCredentials

object Main {

  val baseURL = "https://api.spotify.com/v1"
  val tokenEndpoint = "https://accounts.spotify.com/api/token"

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    val s = Spotify(ClientCredentials(clientID, clientSecret))
    println(s.getAlbums(List("41MnTivkwTO3UUJ8DrqEJJ", "6JWc4iAiJ9FjyK0B59ABb4", "6UXCm6bOO4gFlDQZV5yL37")))
  }

}
