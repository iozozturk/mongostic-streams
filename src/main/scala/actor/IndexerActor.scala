package actor

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.HttpRequest
import play.api.libs.json.JsObject

/**
  * Created by trozozti on 30/04/16.
  */
class IndexerActor extends Actor {

  implicit val _system = stream.Main.system
  implicit val materializer = stream.Main.materializer

  override def receive = {
    case doc@JsObject(_) =>
      println(s"indexing to elastic: $doc")
      Http().singleRequest(HttpRequest(POST, "http://localhost:9200/rightnow/faq", entity = doc.toString()))
    case _ =>
      println(s"unexpected msg")
  }

}
