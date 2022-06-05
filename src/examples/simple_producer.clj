(ns examples.simple-producer
  (:import (org.apache.kafka.clients.producer ProducerConfig Callback ProducerRecord KafkaProducer)
           (org.apache.kafka.common.serialization StringSerializer)
           (java.util Properties Map)))

#_(def producer-properties
  (doto (Properties.)
    (.put ProducerConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG (.getName StringSerializer))
    (.put ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG (.getName StringSerializer))))

(def producer-configs
  {ProducerConfig/BOOTSTRAP_SERVERS_CONFIG      "localhost:9092"
   ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG   (.getName StringSerializer)
   ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG (.getName StringSerializer)})

(def callback
  (reify Callback
    (onCompletion [_this metadata exception]
      (if exception
        (println "Error:" exception)
        (println "Produced to:" (.topic metadata)
                 "Partition:" (.partition metadata)
                 "Offset:" (.offset metadata))))))

(defn produce [value]
  (with-open [producer (KafkaProducer. ^Map producer-configs)]
    (let [record (ProducerRecord. "test-topic-001" value)]
      (.send producer record callback))))

(comment
  (produce (str (random-uuid)))

  (dotimes [_ 5] (produce (str (random-uuid)))))
