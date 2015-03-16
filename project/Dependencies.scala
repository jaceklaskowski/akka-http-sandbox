import sbt._

object Version {
  val akkaHttp  = "1.0-M4"
  val scala     = "2.11.6"
  val specs2    = "3.0.1"
}

object Library {
  val akkaHttpSprayJson = "com.typesafe.akka"  %% "akka-http-spray-json-experimental" % Version.akkaHttp
  val specs2            = "org.specs2"         %% "specs2-core"                       % Version.specs2
}

object Dependencies {

  import Library._

  val all = Seq(
    akkaHttpSprayJson,
    specs2 % "test"
  )
}
