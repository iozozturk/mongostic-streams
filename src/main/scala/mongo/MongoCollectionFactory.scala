package mongo

import com.google.inject.Singleton
import com.mongodb.client.model.Filters
import org.mongodb.scala.MongoDatabase

@Singleton
class MongoCollectionFactory {

  def makeCollection(collName: String, db: MongoDatabase = Mongo.db) = new MongoCollection(collName, db)

  class MongoCollection(val collName: String, db: MongoDatabase) {

    def coll = db.getCollection(collName)

    def find() =  {
      coll.find(Filters.ne("_id", 1))
    }

  }

}
