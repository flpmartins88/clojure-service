(ns examples.simple-consumer
  (:import (org.apache.kafka.clients.producer ProducerConfig)
           (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
           (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)
           (java.time Duration)))

(def consumer-configs
  {ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG        "localhost:9092"
   ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG   (.getName StringDeserializer)
   ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG (.getName StringDeserializer)
   ConsumerConfig/GROUP_ID_CONFIG                 "grupo-clojure-001"
   ConsumerConfig/AUTO_OFFSET_RESET_CONFIG        "earliest"})

(defn- consumer-record [record]
  (println "Consumed event:" (.value record)
           "Key:" (.key record)
           "Partition:" (.partition record)
           "Offset:" (.offset record)))

(defn consume []
  (with-open [consumer (KafkaConsumer. consumer-configs)]
    (.subscribe consumer ["test-topic-001"])
    (loop [records []]
      (if (not (empty? records))
        (apply consumer-record records))
      (recur (seq (.poll consumer (Duration/ofSeconds 1)))))))

(defn -main [& args]
  (consume))
