package com.github.gondola.spotify4s

import java.util.Base64

import com.github.gondola.spotify4s.auth.ClientCredentials

object Main {

  val baseURL = "https://api.spotify.com/v1"
  val tokenEndpoint = "https://accounts.spotify.com/api/token"

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    println(Spotify(ClientCredentials(clientID, clientSecret)).authObj)
  }

}
