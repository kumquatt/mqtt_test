package test.paho

import org.eclipse.paho.client.mqttv3.{MqttMessage, MqttConnectOptions, MqttClient}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

object EmtpyPublish extends App{
  val topic: String = "Mqtt Examples"
  val content: String = "Message from MqttPublishSample"
  val qos: Int = 2
  val broker: String = "tcp://localhost:1883"
  val clientId = "ScalaSample"
  val persistence = new MemoryPersistence

  val sampleClient = new MqttClient(broker, clientId, persistence)
  val option = new MqttConnectOptions
  option.setCleanSession(true)

  println("Connecting to broker: " + broker)
  sampleClient.connect(option)
  println("Connected")
  println("Publishing message: " + content)

  val message = new MqttMessage(content.getBytes)
  message.setQos(qos)

  sampleClient.publish(topic, message)
  println("Message published")

  sampleClient.disconnect()
  println("Disconnected")
}
