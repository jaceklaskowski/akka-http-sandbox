import sbt._

object Version {
  val akkaHttp  = "1.0-M2"
  val scala     = "2.11.5"
  val specs2    = "2.4.15"
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
