package mongo

import com.typesafe.config.{Config, ConfigFactory}
import org.mongodb.scala.{MongoClient, MongoDatabase}

/**
 * Created by ismet on 07/12/15.
 */
object Mongo {

  val client: MongoClient = MongoClient()

  private val config: Config = ConfigFactory.load(this.getClass.getClassLoader, "application.conf")

  private val dbName: String = config.getString("mongo.name")

  val db: MongoDatabase = client.getDatabase(dbName)

}
