package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.ClientCredentials

object Main {

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    val s = Spotify(ClientCredentials(clientID, clientSecret))
    println(s.getShowEpisodes("38bS44xjbVVZ3No3ByF1dJ", offset = 1, limit = 2, market = "SE"))
  }

}
