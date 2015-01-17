name         := """akka-http-sandbox"""
organization := "pl.japila"
version      := "1.0"
scalaVersion := "2.11.5"

libraryDependencies += "org.specs2" %% "specs2-core" % "2.4.15" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0-M2"

Revolver.settings

initialCommands :=
  """
    |import akka.http.server._
  """.stripMargin
