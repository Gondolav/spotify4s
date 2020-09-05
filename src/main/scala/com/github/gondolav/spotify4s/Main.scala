package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.auth.ClientCredentials

object Main {

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    val s = Spotify(ClientCredentials(clientID, clientSecret))
    println(s.getEpisodes(List("77o6BIVlYM3msb4MMIL1jH","0Q86acNRm6V9GYx55SXKwf")))
  }

}
