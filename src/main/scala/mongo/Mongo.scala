package mongo

import org.mongodb.scala.{MongoClient, MongoDatabase}

/**
  * Created by ismet on 07/12/15.
  */
object Mongo {

  val client: MongoClient = MongoClient()

  private val dbName: String = "test-preview"//ConfigFactory.parseString("mongo").getString("name")

  val db: MongoDatabase = client.getDatabase(dbName)

}
