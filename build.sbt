name := """scala-demo"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).disablePlugins(PlayFilters)

scalaVersion := "2.11.8"

routesGenerator := InjectedRoutesGenerator

fork in run := true

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

resolvers += "ximalaya-release" at "http://artifactory.ximalaya.com/artifactory/libs-releases/"

resolvers += "ximalaya-snapshots" at "http://artifactory.ximalaya.com/artifactory/repo/"

val springVersion = "4.3.1.RELEASE"

libraryDependencies ++= Seq(
  guice,
  ws,
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "org.springframework" % "spring-core" % springVersion,
  "org.springframework" % "spring-context" % springVersion,
  "org.springframework" % "spring-beans" % springVersion,
  "org.springframework" % "spring-context-support" % springVersion,
  "org.springframework.guice" % "spring-guice" % "1.0.0",
  "org.yaml" % "snakeyaml" % "1.16",
  "org.springframework.data" % "spring-data-redis" % "1.5.2.RELEASE",
  "redis.clients" % "jedis" % "2.7.3"
)
//EclipseKeys.preTasks := Seq(compile in Compile)
