package com.github.gondolav.spotify4s.entities

class UnavailableException(msg: String) extends Exception(msg) {
  def this() = this("")
}
