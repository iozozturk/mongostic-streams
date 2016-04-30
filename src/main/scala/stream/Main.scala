package stream

import actor.IndexerActor
import akka.actor.{ActorSystem, Props}
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

object Main extends App {

  implicit val system = ActorSystem("MongosticSystem")
  implicit val materializer = ActorMaterializer()

  system.actorOf(Props[IndexerActor], "elastic-actor")

  import system.dispatcher

  val coll = new MongoCollectionFactory().makeCollection("faq")
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


