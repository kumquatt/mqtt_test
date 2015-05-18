package test.fusesource

import org.fusesource.mqtt.client.{QoS, Topic, MQTT}

object TopicTest extends App{
  val mqtt = new MQTT()
  mqtt.setHost("localhost", 1883)
  mqtt.setClientId("client_1")
  mqtt.setCleanSession(true)
  mqtt.setVersion("3.1.1")

  val client1 = mqtt.blockingConnection()
  client1.connect()

  mqtt.setClientId("client_2")
  val client2 = mqtt.blockingConnection()
  client2.connect()

  // client1.subscribe("a/#", 2)
  val topic = new Topic("a/#", QoS.EXACTLY_ONCE)
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))
  client1.subscribe(Array(topic))

  // "a/b", "a/b topic qos 0"
  client2.publish("a/b", "a/b topic qos 0".getBytes, QoS.AT_LEAST_ONCE, false)
  val msg1 = client1.receive()
  println(new String(msg1.getPayload))


  client2.publish("a/b", "a/b topic qos 1".getBytes, QoS.AT_LEAST_ONCE, false)
  val msg2 = client1.receive()
  msg2.ack()
  println(new String(msg2.getPayload))

  client2.publish("a/b", "a/b topic qos 2".getBytes, QoS.AT_LEAST_ONCE, false)
  val msg3 = client1.receive()
  msg3.ack()
  println(new String(msg3.getPayload))

  client1.disconnect()
  client2.disconnect()

}
