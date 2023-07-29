(ns service.system.components.kafka-producer
  (:require [com.stuartsierra.component :as component]
            [service.system.protocols.kafka-producer :as protocols.kafka-producer])
  (:import (org.apache.kafka.clients.producer ProducerConfig Callback ProducerRecord)
           (org.apache.kafka.common.serialization StringSerializer)
           (java.util Map)))

(def producer-configs
  {ProducerConfig/BOOTSTRAP_SERVERS_CONFIG      "localhost:9092"
   ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG   (.getName StringSerializer)
   ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG (.getName StringSerializer)})

(def callback
  (reify Callback
    (onCompletion [_this metadata exception]
      (if exception
        (println "Error:" exception)
        (println {:topic     (.topic metadata)
                  :partition (.partition metadata)
                  :offset    (.offset metadata)})))))


(defrecord KafkaProducer
  [producer]
  component/Lifecycle
  (start [this]
    (when (not producer)
      (let [local-producer (org.apache.kafka.clients.producer.KafkaProducer. ^Map producer-configs)]
        (assoc this :producer local-producer))))

  (stop [this]
    (.close (:producer this))
    (assoc this :producer nil))

  protocols.kafka-producer/KafkaProducer
  (produce! [this topic key value]
    (let [record (ProducerRecord. topic (str key) (str value))]
      (.send (:producer this) record callback))))

(defn new-kafka-producer
  []
  (map->KafkaProducer {}))
