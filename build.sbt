name                := """akka-http-sandbox"""
organization        := "pl.japila.akka"
version             := "1.0"
scalaVersion        := Version.scala
libraryDependencies := Dependencies.all

Revolver.settings

initialCommands :=
  """
    |import akka.http.server._
  """.stripMargin
