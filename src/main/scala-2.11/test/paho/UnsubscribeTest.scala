package test.paho

import org.eclipse.paho.client.mqttv3._

case class MyMqttCallback(clientName: String) extends MqttCallback {
  val messages = scala.collection.mutable.MutableList[(String, MqttMessage)]()

  def received(topic: String, message: MqttMessage): Unit = {
    println(topic + " " + message)
    messages.+=((topic, message))
  }

  def getMessages = messages.toList

  def clearMessages = messages.clear

  override def deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken): Unit = {

  }

  override def messageArrived(s: String, mqttMessage: MqttMessage): Unit = {
    received(s, mqttMessage)
    println("{} => topic: {} message {}", clientName, s, new String(mqttMessage.getPayload))
  }

  override def connectionLost(throwable: Throwable): Unit = {

  }
}

object UnsubscribeTest extends App {

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
  client1.subscribe("a/b", 2)
  client1.subscribe("a/+", 2)
  client2.subscribe("a/#", 2)

  client2.publish("a/b", "a/b 1111".getBytes, 0, false)

  println("-----")
  Thread.sleep(1000)

  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 3)
  callback_client1.clearMessages
  callback_client2.clearMessages

  client1.unsubscribe("a/b")
  client2.publish("a/b", "a/b 22222".getBytes, 0, false)
  println("-----")
  Thread.sleep(1000)

  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 2)

  callback_client1.clearMessages
  callback_client2.clearMessages

  client1.unsubscribe("a/+")
  client2.publish("a/b", "a/b 33333".getBytes, 0, false)
  println("-----")
  Thread.sleep(1000)

  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 1)

  callback_client1.clearMessages
  callback_client2.clearMessages

  client2.unsubscribe("a/#")
  client2.publish("a/b", "a/b 44444".getBytes, 0, false)

  println("-----")
  Thread.sleep(1000)

  assert((callback_client1.getMessages ::: callback_client2.getMessages).size == 0)

  client1.disconnect()
  client1.close()

  client2.disconnect()
  client2.close()

}
