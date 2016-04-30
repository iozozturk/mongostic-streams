name := "mongostic-streams"

version := "1.0"

scalaVersion := "2.11.7"

val akkaV = "2.4.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-remote" % akkaV,
  "com.typesafe.akka" %% "akka-stream" % akkaV,
  "com.typesafe.akka" %% "akka-http-core" % akkaV,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0",
  "com.google.inject" % "guice" % "4.0",
  "com.typesafe.play" %% "play-json" % "2.5.2"
)

fork in run := true
