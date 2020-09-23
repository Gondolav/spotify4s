package com.github.gondolav.spotify4s.entities

import java.net.URI

import utest._

object AlbumTests extends TestSuite {
  val tests = Tests {
    val albumGroup: Option[String] = Some("group")
    val albumType: String = "album"
    val artists: List[ArtistJson] = Nil
    val availableMarkets: List[String] = Nil
    val copyrights: Option[List[CopyrightJson]] = Some(Nil)
    val externalIds: Option[Map[String, String]] = Some(Map("x" -> "y"))
    val externalUrls: Map[String, String] = Map("x" -> "y")
    val genres: Option[List[String]] = Some(Nil)
    val href: String = "href"
    val id: String = "id"
    val images: List[Image] = Nil
    val label: Option[String] = Some("label")
    val name: String = "name"
    val popularity: Option[Int] = Some(10)
    val releaseDate: String = "2020"
    val releaseDatePrecision: String = "year"
    val restrictions: Option[Restrictions] = Some(Restrictions("reason"))
    val tracks: Option[Paging[TrackJson]] = None
    val `type`: String = "album"
    val uri: String = "uri:string"

    val albumJson = AlbumJson(albumGroup, albumType, artists, availableMarkets, copyrights, externalIds, externalUrls,
      genres, href, id, images, label, name, popularity, releaseDate, releaseDatePrecision, restrictions, tracks,
      `type`, uri)

    val album = Album(albumGroup, AlbumType.fromString(albumType), artists.map(Artist.fromJson),
      availableMarkets, copyrights.map(x => x.map(Copyright.fromJson)), externalIds, externalUrls, genres, href, id,
      images, label, name, popularity, releaseDate, ReleaseDatePrecision.fromString(releaseDatePrecision),
      restrictions, tracks.map(x => x.copy(items = x.items.map(y => y.map(Track.fromJson)))),
      ObjectType.fromString(`type`), URI.create(uri))

    test("Album.fromJson correctly works") {
      assert(Album.fromJson(albumJson) == album)
    }

    test("AlbumType.fromString correctly works") {
      val album = "album"
      val single = "single"
      val compilation = "compilation"

      assert(AlbumType.fromString(album) == AlbumT)
      assert(AlbumType.fromString(single) == SingleT)
      assert(AlbumType.fromString(compilation) == CompilationT)
    }

    test("SavedAlbum.fromJson correctly works") {
      val addedAt = "added_at"
      val json = SavedAlbumJson(addedAt, albumJson)
      assert(SavedAlbum.fromJson(json) == SavedAlbum(addedAt, album))
    }
  }
}