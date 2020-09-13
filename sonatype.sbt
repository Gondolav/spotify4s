publishMavenStyle := true

licenses := Seq("MIT" -> url("https://github.com/Gondolav/spotify4s/blob/master/LICENSE"))

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("Gondolav", "spotify4s", "avenezia.ch@gmail.com"))