name := "spotify4s"

version := "0.1.1"

scalaVersion := "2.13.3"

libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.5"
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.2" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)

git.remoteRepo := "git@github.com:Gondolav/spotify4s.git"

excludeFilter in ghpagesCleanSite :=
  ((f: File) => (ghpagesRepository.value / "index.html").getCanonicalPath == f.getCanonicalPath)

publishTo := sonatypePublishToBundle.value

