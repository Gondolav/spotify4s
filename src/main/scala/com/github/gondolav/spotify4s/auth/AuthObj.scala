package com.github.gondolav.spotify4s.auth

import upickle.default._

private case class AuthObjJson(access_token: String, token_type: String, expires_in: Long, refresh_token: String = "", scope: String = "")
private object AuthObjJson {
  implicit val rw: ReadWriter[AuthObjJson] = macroRW
}

case class AuthObj(accessToken: String, tokenType: String, expiresIn: Long, refreshToken: String = "", scopes: List[String] = Nil)
object AuthObj {
  def fromJson(json: AuthObjJson): AuthObj = AuthObj(json.access_token, json.token_type, json.expires_in, json.refresh_token, json.scope.split("\\W+").toList)
}


