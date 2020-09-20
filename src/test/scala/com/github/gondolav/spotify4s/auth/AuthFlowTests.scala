package com.github.gondolav.spotify4s.auth

import com.github.gondolav.spotify4s.Constants.{clientID, clientSecret}
import utest._

object AuthFlowTests extends TestSuite {
  val tests = Tests {
    test("ClientCredentials correctly authenticates") {
      val cc = ClientCredentials(clientID, clientSecret)
      assertMatch(cc.authenticate) { case Right(_: AuthObj) => }
    }

    test("ClientCredentials fails with wrong credentials") {
      val cc = ClientCredentials("", "")
      assertMatch(cc.authenticate) { case Left(_: AuthError) => }
    }

    test("ClientCredentials should fail when requesting refreshed token") {
      val cc = ClientCredentials(clientID, clientSecret)
      val authError = AuthError("Cannot refresh token", Some("Cannot refresh token with client credentials flow"))
      assert(cc.requestRefreshedToken("").swap.getOrElse(null) == authError)
    }
  }
}
