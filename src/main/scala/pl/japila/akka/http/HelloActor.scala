package pl.japila.akka.http

import akka.actor.Actor
import akka.stream.actor.{ActorSubscriber, RequestStrategy, ZeroRequestStrategy}
import pl.japila.akka.http.HelloActor.Person

class HelloActor extends Actor with ActorSubscriber {
  override def receive: Receive = {
    case p: Person =>
      println(p)
      sender ! p
  }

  override protected def requestStrategy: RequestStrategy = ZeroRequestStrategy
}

object HelloActor {
  case class Person(name: String)
}
