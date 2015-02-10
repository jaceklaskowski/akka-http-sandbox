import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.stream.ActorFlowMaterializer
import akka.stream.actor.{WatermarkRequestStrategy, RequestStrategy, ActorSubscriber}
import akka.stream.scaladsl.{Flow, Source, Sink}
import org.specs2._

class HelloSpec extends Specification { def is = s2"""

  This is a specification to learn Akka Streams/HTTP API

  the API should
    run a Akka Streams flow   $e1
"""

  class MyActor extends ActorSubscriber {

    override protected def requestStrategy: RequestStrategy = WatermarkRequestStrategy(10)

    override def receive: Actor.Receive = {
      case s: String => sender() ! "thanks"
    }
  }

  def e1 = {
    implicit val system = ActorSystem("reactive-tweets")
    implicit val mat = ActorFlowMaterializer()

    val subscriber = Props(new MyActor)

    Source(0 to 3).map(_ * 2).runWith(Sink(subscriber))

    success
  }
}
