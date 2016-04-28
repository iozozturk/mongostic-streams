package stream

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Source}
import mongo.MongoCollectionFactory
import org.mongodb.scala.{Document, FindObservable}
import play.api.libs.json.{JsObject, Json}
import rxStreams.Implicits._

object MongoToElasticsearch extends App {

  implicit val system = ActorSystem("Sys")

  import system.dispatcher

  implicit val materializer = ActorMaterializer()

  val coll = new MongoCollectionFactory().makeCollection("galleries")

  val find: FindObservable[Document] = coll.find()

  def index = Flow[JsObject].map { doc =>
    println(s"Indexing $doc")
    Http().singleRequest(HttpRequest(POST, "http://localhost:9200/rightnow/faq", entity = doc.toString()))
  }

  def toJson = Flow[Document].map{ doc=>
    Json.parse(doc.toJson()).as[JsObject] - "_id"
  }

  Source.fromPublisher(find)
    .via(toJson)
    .via(index)
    .runForeach(d => println(s"Indexed Document: \n $d"))
    .onComplete(_ => println("Indexing complete"))

}


