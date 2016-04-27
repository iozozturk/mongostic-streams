package stream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import mongo.MongoCollectionFactory
import org.mongodb.scala.{Document, FindObservable}
import rxStreams.Implicits._

object MongoToElasticsearch  {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Sys")
    import system.dispatcher

    implicit val materializer = ActorMaterializer()


    val coll = new MongoCollectionFactory().makeCollection("products")

    val find: FindObservable[Document] = coll.find()

    val source: Source[Document, NotUsed] = Source.fromPublisher(find)

    source.runForeach(d => println(s"Indexing Document: \n $d"))
      .onComplete(_=>println("Indexing complete"))

  }
}