package test.fusesource

import org.fusesource.mqtt.client.{QoS, Topic, MQTT}

object EmptyPublish extends App {
  val mqtt = new MQTT()
  mqtt.setHost("localhost", 1883)
  mqtt.setClientId("ScalaSample")
  mqtt.setCleanSession(true)
  mqtt.setVersion("3.1.1")
  val connection = mqtt.blockingConnection()
  println("Connecting to broker: " + connection)

  connection.connect()
  println("Connected")

  val topic = "Mqtt Examples"
  val content = "Message from MqttPublishSample"

  println("Publishing message: " + content)
  connection.publish(topic, content.getBytes, QoS.EXACTLY_ONCE, false)
  println("Message published")

  connection.disconnect()
  println("Disconnected")
}
