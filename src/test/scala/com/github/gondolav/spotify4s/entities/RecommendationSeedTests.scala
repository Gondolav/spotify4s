package com.github.gondolav.spotify4s.entities

import utest._

object RecommendationSeedTests extends TestSuite {
  val tests = Tests {
    test("RecommendationSeed is correctly created") {
      val afterFilteringSize = 2
      val afterRelinkingSize = 10
      val href = "href"
      val id = "id"
      val objectType = "type"
      val initialPoolSize = 10

      val recommendationSeed = RecommendationSeed(afterFilteringSize, afterRelinkingSize, href, id, initialPoolSize, objectType)
      assert(recommendationSeed.afterFilteringSize == afterFilteringSize)
      assert(recommendationSeed.afterRelinkingSize == afterRelinkingSize)
      assert(recommendationSeed.href == href)
      assert(recommendationSeed.id == id)
      assert(recommendationSeed.initialPoolSize == initialPoolSize)
      assert(recommendationSeed.`type` == objectType)
    }
  }
}