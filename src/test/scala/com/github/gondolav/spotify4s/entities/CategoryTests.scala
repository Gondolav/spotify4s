package com.github.gondolav.spotify4s.entities

import utest._

object CategoryTests extends TestSuite {
  val tests = Tests {
    test("Category is correctly created") {
      val href = "href"
      val icons = Nil
      val id = "id"
      val name = "name"

      val category = Category(href, icons, id, name)
      assert(category.href == href)
      assert(category.icons == icons)
      assert(category.id == id)
      assert(category.name == name)
    }
  }
}