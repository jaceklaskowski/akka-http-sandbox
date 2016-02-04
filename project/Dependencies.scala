import sbt._

object Version {
  val akkaHttp  = "2.4.2-RC2"
  val scala     = "2.11.7"
  val specs2    = "3.7"
}

object Library {
  val akkaHttp          = "com.typesafe.akka"  %% "akka-http-experimental"            % Version.akkaHttp
  val akkaHttpSprayJson = "com.typesafe.akka"  %% "akka-http-spray-json-experimental" % Version.akkaHttp
  val specs2            = "org.specs2"         %% "specs2-core"                       % Version.specs2
}

object Dependencies {

  import Library._

  val all = Seq(
    akkaHttp,
    akkaHttpSprayJson,
    specs2 % "test"
  )
}
