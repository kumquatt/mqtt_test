name := "mqtt_test"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= {
  Seq(
    "repo" at "http://repo.typesafe.com/typesafe/releases/",
    "Paho MQTT Client" at "https://repo.eclipse.org/content/repositories/paho-releases/"
  )
}

libraryDependencies ++= {
  val akkaVersion = "2.3.9"

  Seq(
    "junit" % "junit" % "4.10",
    "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.0.2",
    "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
    "com.typesafe" % "config" % "1.2.1",
    "org.fusesource.mqtt-client" % "mqtt-client" % "1.10",
    "net.sigusr" %% "scala-mqtt-client" % "0.6.0"
  )
}
