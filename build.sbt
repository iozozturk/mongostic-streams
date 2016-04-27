import scalariform.formatter.preferences._

name := "mongostic-streams"

version := "1.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.2",
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0",
  "com.google.inject" % "guice" % "4.0",
  "com.typesafe.play" %% "play-json" % "2.5.2"
)

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

fork in run := true
