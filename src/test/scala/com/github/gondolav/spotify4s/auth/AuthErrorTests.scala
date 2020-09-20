package com.github.gondolav.spotify4s.auth

import utest._

object AuthErrorTests extends TestSuite {
  val tests = Tests {
    test("fromJson correctly works") {
      val error = "error"
      val errorDescription = Some("errorDescription")
      val json = AuthErrorJson(error, errorDescription)
      assert(AuthError.fromJson(json) == AuthError(error, errorDescription))
    }
  }
}
