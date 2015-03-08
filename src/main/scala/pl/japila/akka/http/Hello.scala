package pl.japila.akka.http

import java.io.File

import akka.actor.{Props, ActorSystem}
import akka.http.Http.{IncomingConnection, ServerBinding}
import akka.http.marshallers.sprayjson.SprayJsonSupport
import akka.http.model.{HttpRequest, HttpResponse}
import akka.http.server
import akka.stream.scaladsl.{Sink, Source, Flow}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import pl.japila.akka.http.HelloActor.Person
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

object Hello extends App {
  println("Hello, Akka-HTTP world!")

  val conf = ConfigFactory.parseString( """
    akka.loglevel         = INFO
    akka.log-dead-letters = off
                                        """)
  implicit val system = ActorSystem("ServiceDiscovery", conf)
  val helloActor = system.actorOf(Props[HelloActor])

  import akka.http.Http
  import system.dispatcher

  import akka.stream.ActorFlowMaterializer
  implicit val mat = ActorFlowMaterializer()

  val binding: Source[IncomingConnection, Future[ServerBinding]] = Http().bind("localhost", 8080)

  import akka.http.server.Directives._

  object PersonJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val personFormat = jsonFormat1(Person)
  }
  import PersonJsonSupport._
  import HelloActor.Person

  val updatePerson = (person: Person) => {
    import akka.pattern.ask
    import scala.concurrent.duration._
    implicit val timeout = Timeout(2.seconds)
    (helloActor ? person).mapTo[Person].map { r =>
      println(s"Received: $r")
      r
    }
  }

  val route: server.Route =
    (post & path("actor")) {
      handleWith(updatePerson)
    } ~
    getFromFile("src/main/resources/hello.html")

  binding.runWith(Sink.foreach { connection =>
    println("Accepted new connection from " + connection.remoteAddress)
    connection.handleWith(route)
  })

}
