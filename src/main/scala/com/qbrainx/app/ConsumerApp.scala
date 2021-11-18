package com.qbrainx.app

import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Sink
import org.apache.kafka.common.serialization.StringDeserializer
import com.qbrainx.util.Implicits._
import com.qbrainx.config.AppConfig

object ConsumerApp extends App {

  val topic: String = AppConfig.topic
  println(topic)
  val consumerProperties=AppConfig.consumerConfig
  val config=system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(consumerProperties, new StringDeserializer, new StringDeserializer)
println(".>>>>>>>>>>")
  Consumer
    .plainSource(consumerSettings, Subscriptions.topics(topic))
    .map(f=>f*f)
    .map({f=>
      println(s"Square Root of value is $f")
          f
    })
    .async
    .map({
         f=>println(s"CubeRoot of value is $f" )
         f
    })
    .runWith(Sink.ignore)

}
