package com.github.gondolav.spotify4s.auth

import utest._

object AuthExceptionTests extends TestSuite {
  val tests = Tests {
    test("AuthException is correctly created") {
      val message = "message"
      val authError = AuthError("error", Some("errorDescription"))
      val exception = new AuthException(message, authError)
      assert(exception.getMessage == message)
      assert(exception.authError == authError)
    }
  }
}
