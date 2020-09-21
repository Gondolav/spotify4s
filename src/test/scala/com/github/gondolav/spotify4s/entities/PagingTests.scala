package com.github.gondolav.spotify4s.entities

import utest._

object PagingTests extends TestSuite {
  val tests = Tests {
    test("Paging is correctly created") {
      val href = "href"
      val items = Some(List("item"))
      val limit = Some(1)
      val next = Some("next")
      val offset = Some(2)
      val previous = Some("previous")
      val total = 10

      val paging = Paging(href, items, limit, next, offset, previous, total)
      assert(paging.href == href)
      assert(paging.items == items)
      assert(paging.limit == limit)
      assert(paging.next == next)
      assert(paging.offset == offset)
      assert(paging.previous == previous)
      assert(paging.total == total)
    }
  }
}