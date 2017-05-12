name := "alphaSort"

version := "1.0"

scalaVersion := "2.12.2"


libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.5.1",
  "org.json4s" %% "json4s-jackson" % "3.5.1",
  "mysql" % "mysql-connector-java" % "5.0.8",
  "org.hibernate" % "hibernate-core" % "5.2.10.Final",
  "org.hibernate" % "hibernate-entitymanager" % "5.2.10.Final" exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api")
)