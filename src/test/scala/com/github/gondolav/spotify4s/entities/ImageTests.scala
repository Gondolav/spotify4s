package com.github.gondolav.spotify4s.entities

import utest._

object ImageTests extends TestSuite {
  val tests = Tests {
    test("Image is correctly created") {
      val height = 64
      val width = 64
      val url = "url"

      val image = Image(width, height, url)
      assert(image.width == width)
      assert(image.height == height)
      assert(image.url == url)
    }
  }
}