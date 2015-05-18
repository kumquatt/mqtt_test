package test.paho

import org.eclipse.paho.client.mqttv3.{MqttClient, MqttConnectOptions}

object TopicTest extends App {

  val option = new MqttConnectOptions
  val client1 = new MqttClient("tcp://127.0.0.1:1883", "client_1")
  val callback_client1 = MyMqttCallback("client_1")
  client1.setCallback(callback_client1)

  option.setKeepAliveInterval(10)
  client1.connect(option)

  val client2 = new MqttClient("tcp://127.0.0.1:1883", "client_2")
  val callback_client2 = MyMqttCallback("client_2")
  client2.setCallback(callback_client2)
  client2.connect(option)

  /////////////////
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)
  client1.subscribe("a/#", 2)

  client2.publish("a/b", "a/b topic qos 0".getBytes, 0, false)
  Thread.sleep(1000)
  println("--------")
  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 1)
  callback_client1.clearMessages
  callback_client2.clearMessages

  client2.publish("a/b", "a/b topic qos 1".getBytes, 1, false)
  Thread.sleep(1000)
  println("--------")
  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 1)
  callback_client1.clearMessages
  callback_client2.clearMessages

  client2.publish("a/b", "a/b topic qos 2".getBytes, 0, false)
  Thread.sleep(1000)
  println("--------")
  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 1)
  callback_client1.clearMessages
  callback_client2.clearMessages

  client1.disconnect()
  client1.close()

  client2.disconnect()
  client2.close()
}