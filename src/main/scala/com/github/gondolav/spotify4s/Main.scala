package com.github.gondolav.spotify4s

import java.net.URI

import com.github.gondolav.spotify4s.entities.{AlbumObj, ArtistObj}

object Main {

  val clientID = "15c21c078d574f78a8f758160c466315"
  val clientSecret = "d57182bc22304585b6837788f73bdad1"

  def main(args: Array[String]): Unit = {
    val s = Spotify(clientID, clientSecret, URI.create("http://localhost"), List("user-read-private"))
    println(s.search(q = "ghali", objectTypes = List(ArtistObj)))
  }

}
