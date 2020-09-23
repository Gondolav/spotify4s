package com.github.gondolav.spotify4s

import com.github.gondolav.spotify4s.Constants.{clientID, clientSecret}
import com.github.gondolav.spotify4s.entities.{Album, Category, Error, Paging, Playlist, Recommendations, Track}
import utest._

object SpotifyTests extends TestSuite {
  val tests = Tests {
    val sp = Spotify(clientID, clientSecret)

    test("withErrorHandling correctly works") {
      assertMatch(sp.getAlbum("")) {
        case Left(_: Error) =>
      }
    }

    test("getAlbum correctly works") {
      assertMatch(sp.getAlbum("0sNOF9WDwhWunNAHPD3Baj", "US")) {
        case Right(_: Album) =>
      }
    }

    test("getAlbumTracks correctly works") {
      val res = sp.getAlbumTracks("0sNOF9WDwhWunNAHPD3Baj")

      assertMatch(res) {
        case Right(_: Paging[Track]) =>
      }

      res.map(p => assertMatch(p.items.get.head) {
        case _: Track =>
      })
    }

    test("getAlbums correctly works") {
      val res = sp.getAlbums("0sNOF9WDwhWunNAHPD3Baj" :: "6UXCm6bOO4gFlDQZV5yL37" :: Nil)

      assertMatch(res) {
        case Right(_: List[Album]) =>
      }

      res.map(as => assertMatch(as.head) {
        case _: Album =>
      })
    }

    test("getCategory correctly works") {
      assertMatch(sp.getCategory("party")) {
        case Right(_: Category) =>
      }
    }

    test("getCategoryPlaylists correctly works") {
      val res = sp.getCategoryPlaylists("party")

      assertMatch(res) {
        case Right(_: Paging[Playlist]) =>
      }

      res.map(ps => assertMatch(ps.items.get.head) {
        case _: Playlist =>
      })
    }

    test("getCategories correctly works") {
      val res = sp.getCategories()

      assertMatch(res) {
        case Right(_: Paging[Category]) =>
      }

      res.map(ps => assertMatch(ps.items.get.head) {
        case _: Category =>
      })
    }

    test("getFeaturedPlaylists correctly works") {
      val res = sp.getFeaturedPlaylists()

      assertMatch(res) {
        case Right(_: (String, Paging[Playlist])) =>
      }

      res.map(fps => assertMatch(fps._2.items.get.head) {
        case _: Playlist =>
      })
    }

    test("getNewReleases correctly works") {
      val res = sp.getNewReleases()

      assertMatch(res) {
        case Right(_: Paging[Album]) =>
      }

      res.map(nr => assertMatch(nr.items.get.head) {
        case _: Album =>
      })
    }

    test("getRecommendations correctly works") {
      assertMatch(sp.getRecommendations(seedArtists = List("4NHQUGzhtTLFvgF5SZesLK"),
        seedTracks = List("0c6xIDDpzE81m2q797ordA"), attributes = Map("min_energy" -> "0.4", "min_popularity" -> "50"),
        market = "US")) {
        case Right(_: Recommendations) =>
      }
    }
  }
}
