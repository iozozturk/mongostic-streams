package stream

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, Attributes}
import mongo.MongoCollectionFactory
import org.mongodb.scala.Observable
import play.api.libs.json.JsObject
import rxStreams.Implicits._

object MongoToElasticsearch extends App {

  implicit val system = ActorSystem("Sys")
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val coll = new MongoCollectionFactory().makeCollection("galleries")

  val find: Observable[JsObject] = coll.find()

  def index = Flow[JsObject].map { doc =>
    Http().singleRequest(HttpRequest(POST, "http://localhost:9200/rightnow/faq", entity = doc.toString()))
  }

  def massage = Flow[JsObject].map { doc =>
    doc - "_id"
  }

  Source.fromPublisher(find)
    .via(massage)
    .log("indexing-document")
    .withAttributes(Attributes.logLevels(onElement = Logging.WarningLevel))
    .via(index)
    .runWith(Sink.ignore)
    .onComplete(_ => println("Indexing complete"))

}


