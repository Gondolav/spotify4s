package com.github.gondolav.spotify4s.auth

import utest._

object AuthObjTests extends TestSuite {
  val tests = Tests {
    val accessToken = "accessToken"
    val tokenType = "tokenType"
    val expiresIn = 10
    val refreshToken = "refreshToken"
    val scopes = "scope" :: Nil
    val authObj = AuthObj(accessToken, tokenType, expiresIn, refreshToken, scopes)

    test("AuthObj is correctly created") {
      assert(authObj.accessToken == accessToken)
      assert(authObj.tokenType == tokenType)
      assert(authObj.expiresIn == expiresIn)
      assert(authObj.refreshToken == refreshToken)
      assert(authObj.scopes == scopes)
    }

    test("fromJson correctly works") {
      val json = AuthObjJson(accessToken, tokenType, expiresIn, refreshToken, scopes.mkString)
      assert(AuthObj.fromJson(json) == authObj)
    }
  }
}
