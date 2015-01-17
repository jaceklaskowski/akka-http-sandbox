package com.example

import java.io.File

import akka.actor.ActorSystem
import akka.http.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.Flow
import com.typesafe.config.ConfigFactory

object Hello extends App {
  println("Hello, Akka-HTTP world!")

  val conf = ConfigFactory.parseString( """
    akka.loglevel         = INFO
    akka.log-dead-letters = off
                                        """)
  implicit val system = ActorSystem("ServiceDiscovery", conf)

  import system.dispatcher

  import akka.http.Http
  val binding = Http().bind("localhost", 8080)

  import akka.stream.FlowMaterializer
  implicit val fm = FlowMaterializer()

  import akka.http.server.Directives._

  println(new File("src/main/resources/hello.html").getAbsolutePath)

  val route: Flow[HttpRequest, HttpResponse] = getFromFile("src/main/resources/hello.html")

  binding startHandlingWith route

}
