package pl.japila.akka.http

import akka.actor.Actor
import akka.stream.actor.{ActorPublisher, ActorPublisherMessage}

/**
 * Based upon http://stackoverflow.com/a/29077212/1305344
 */
class ActorBasedSource extends Actor with ActorPublisher[Int] {

  import akka.stream.actor.ActorPublisherMessage._

  var items: List[Int] = List.empty

  def receive = {
    case s: String =>
      if (totalDemand == 0)
        items = items :+ s.toInt
      else
        onNext(s.toInt)

    case Request(demand) =>
      if (demand > items.size) {
        items foreach onNext
        items = List.empty
      }
      else {
        val (send, keep) = items.splitAt(demand.toInt)
        items = keep
        items foreach onNext
      }


    case other =>
      println(s"got other $other")
  }
}