package mongo

import com.google.inject.Singleton
import com.mongodb.CursorType
import com.mongodb.client.model.Filters
import org.mongodb.scala.MongoDatabase
import play.api.libs.json.{JsObject, Json}

@Singleton
class MongoCollectionFactory {

  def makeCollection(collName: String, db: MongoDatabase = Mongo.db) = new MongoCollection(collName, db)

  class MongoCollection(val collName: String, db: MongoDatabase) {

    def coll = db.getCollection(collName)

    def find() = {
      coll.find()
//        .cursorType(CursorType.TailableAwait) //tailable cursor requires capped(fixed size) buffer collections
//        .noCursorTimeout(true)
        .map { doc =>
        Json.parse(doc.toJson()).as[JsObject]
      }
    }

  }

}
