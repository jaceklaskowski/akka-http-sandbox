import java.io.IOException

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.client.RequestBuilding
import akka.http.marshallers.sprayjson.SprayJsonSupport
import akka.http.model.StatusCodes._
import akka.http.model.{HttpResponse, HttpRequest}
import akka.http.unmarshalling._
import akka.stream.{FlowMaterializer, ActorFlowMaterializer}
import akka.stream.scaladsl.{Sink, Source, Flow}
import org.specs2._
import org.specs2.specification.Grouped
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

class OutgoingConnectionSpec extends Specification with Grouped { def is = s2"""

    Example of OutgoingConnection ${g1.e1}

  """

  new g1 {
    e1 := {
      case class IpInfo(ip: String, country: Option[String], city: Option[String], latitude: Option[Double], longitude: Option[Double])
      object Protocols extends DefaultJsonProtocol with SprayJsonSupport {
        implicit val ipInfoFormat = jsonFormat5(IpInfo.apply)
      }
      import Protocols._

      implicit val system = ActorSystem()
      implicit val executor = system.dispatcher
      lazy val outgoingConn: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] = Http().outgoingConnection("www.telize.com", 80)
      def send(request: HttpRequest)(implicit fm: ActorFlowMaterializer): Future[HttpResponse] = Source.single(request).via(outgoingConn).runWith(Sink.head[HttpResponse]())

      implicit val afm = ActorFlowMaterializer()
      implicit def IpPairUnmarshaller: FromEntityUnmarshaller[IpInfo] = SprayJsonSupport.sprayJsonUnmarshaller[IpInfo]
      send(RequestBuilding.Get(s"/geoip/46.19.37.108")).map { response =>
        response.status match {
          case OK =>
            Unmarshal(response.entity).to[IpInfo].foreach(println)
          case _ =>
            Unmarshal(response.entity).to[String].map { entity =>
              val error = s"Request failed with status code ${response.status} and entity $entity"
              println(error)
            }
        }
      }.onComplete(println)
      success
    }
  }
}
