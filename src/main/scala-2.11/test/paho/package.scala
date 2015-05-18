package test

import org.eclipse.paho.client.mqttv3.{IMqttDeliveryToken, MqttMessage, MqttCallback}

package object paho {
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
}
