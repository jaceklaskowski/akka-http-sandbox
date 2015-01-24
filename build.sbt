name                := """akka-http-sandbox"""
organization        := "pl.japila.akka"
version             := "1.0"
scalaVersion        := Version.scala
libraryDependencies := Dependencies.all

Revolver.settings

initialCommands :=
  """
    |import akka.actor.ActorSystem
    |import akka.http.server._
    |import akka.stream._
    |import akka.stream.scaladsl._
  """.stripMargin
