package com.github.gondolav.spotify4s.entities

import utest._

object FollowersTests extends TestSuite {
  val tests = Tests {
    test("Followers is correctly created") {
      val href = "href"
      val total = 42

      val followers = Followers(href, total)
      assert(followers.href == href)
      assert(followers.total == total)
    }
  }
}