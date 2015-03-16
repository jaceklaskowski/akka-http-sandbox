import java.util.concurrent.TimeUnit

import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.stream.ActorFlowMaterializer
import akka.stream.actor.{ActorPublisher, WatermarkRequestStrategy, RequestStrategy, ActorSubscriber}
import akka.stream.scaladsl.{Flow, Source, Sink}
import org.reactivestreams.Publisher
import org.specs2._
import pl.japila.akka.http.ActorBasedSource

class HelloSpec extends Specification { def is = s2"""

  This is a specification to learn Akka Streams/HTTP API

  the API should
    run a Akka Streams flow                                  $e1
    run Akka Streams flow with ActorPublisher as the Source  $e2
"""

  class MyActor extends ActorSubscriber {

    override protected def requestStrategy: RequestStrategy = WatermarkRequestStrategy(10)

    override def receive: Actor.Receive = {
      case s =>
        println(s"${Thread.currentThread()}...received: [$s]")
        // sender() ! "thanks"
    }
  }

  def e1 = {
    implicit val system = ActorSystem("akka-http-sandbox")
    implicit val mat = ActorFlowMaterializer()

    val subscriber = Props(new MyActor)

    println(s"${Thread.currentThread()}")

    Source(0 to 3).map(_ * 2).runWith(Sink(subscriber))

    success
  }

  def e2 = {
    implicit val system = ActorSystem("akka-http-sandbox")
    implicit val mat = ActorFlowMaterializer()

    val actorRef = system.actorOf(Props[ActorBasedSource])
    val publisher = ActorPublisher[Int](actorRef)

    println(s"${Thread.currentThread()}")

    Source(publisher).map(_ * 2).runWith(Sink.foreach(println))

    (1 until 20).foreach { n =>
      actorRef ! n.toString
      TimeUnit.MILLISECONDS.sleep(5)
    }

    success
  }
}
