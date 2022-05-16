(ns service.system.components.kafka
  (:require [com.stuartsierra.component :as component]
            [service.system.protocols.kafka :as protocols.kafka])
  (:import (org.apache.kafka.clients.producer ProducerConfig Callback KafkaProducer ProducerRecord)
           (org.apache.kafka.common.serialization StringSerializer)))

(def producer-configs
  {ProducerConfig/BOOTSTRAP_SERVERS_CONFIG      "localhost:9092"
   ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG   (.getName StringSerializer)
   ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG (.getName StringSerializer)})

(def callback
  (reify Callback
    (onCompletion [this metadata exception]
      (if exception
        (println "Error:" exception)
        (println "Produced to:" (.topic metadata)
                 "Partition:" (.partition metadata)
                 "Offset:" (.offset metadata))))))

(defn produce! [producer topic key value]
  (let [record (ProducerRecord. topic key value)]
    (.send producer record callback)))

(defrecord Kafka
  []
  component/Lifecycle
  (start [this]
    (let [producer (KafkaProducer. producer-configs)]
      (assoc this :producer producer)))

  (stop [this]
    (assoc this :producer nil))

  protocols.kafka/Kafka
  (produce! [this topic value]
    (produce! this topic nil value))

  (produce! [this topic key value]
    (produce! this topic key value)))

(defn new-kafka
  []
  (map->Kafka {}))
