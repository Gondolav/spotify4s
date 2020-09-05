package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.ClientCredentials

object Main {

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    val s = Spotify(ClientCredentials(clientID, clientSecret))
    println(s.getRecommendations(seedArtists = List("4NHQUGzhtTLFvgF5SZesLK"), seedTracks = List("0c6xIDDpzE81m2q797ordA"), attributes = Map("min_energy" -> "0.4", "min_popularity" -> "50"), market = "US"))
  }

}
