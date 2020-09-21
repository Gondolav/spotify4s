package com.github.gondolav.spotify4s.entities

import utest._

object RestrictionsTests extends TestSuite {
  val tests = Tests {
    test("Restrictions is correctly created") {
      val reason = "reason"

      val restrictions = Restrictions(reason)
      assert(restrictions.reason == reason)
    }
  }
}