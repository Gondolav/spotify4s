publishMavenStyle := true

description := "An intuitive and lightweight Scala library for the Spotify Web API"

organization := "com.github.gondolav"
organizationName := "Gondolav"
organizationHomepage := Some(url("https://github.com/Gondolav"))

licenses := Seq("MIT" -> url("https://github.com/Gondolav/spotify4s/blob/master/LICENSE"))

import xerial.sbt.Sonatype._

sonatypeProfileName := "com.github.gondolav"

sonatypeProjectHosting := Some(GitHubHosting("Gondolav", "spotify4s", "avenezia.ch@gmail.com"))