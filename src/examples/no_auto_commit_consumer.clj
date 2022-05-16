(ns examples.no-auto-commit-consumer
  (:import (org.apache.kafka.clients.consumer ConsumerConfig OffsetAndMetadata KafkaConsumer)
           (org.apache.kafka.common.serialization StringDeserializer)
           (org.apache.kafka.common TopicPartition)
           (java.time Duration)))

(def consumer-configs
  {ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG        "localhost:9092"
   ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG   (.getName StringDeserializer)
   ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG (.getName StringDeserializer)
   ConsumerConfig/GROUP_ID_CONFIG                 "grupo-clojure-001"
   ConsumerConfig/AUTO_OFFSET_RESET_CONFIG        "earliest"
   ConsumerConfig/ENABLE_AUTO_COMMIT_CONFIG       false
   ConsumerConfig/MAX_POLL_RECORDS_CONFIG         "1"})

(defn- consume-record
  "Consome o evento do kafka. Deve retornar boolean se teve sucesso ou nâo"
  [record]
  (println "Consumed event:" (.value record)
           "Key:" (.key record)
           "Partition:" (.partition record)
           "Offset:" (.offset record))

  (not (= (.offset record) 48)))

(defn build-commit-map
  ([record] (build-commit-map (.topic record) (.partition record) (.offset record)))
  ([topic partition offset] {(TopicPartition. topic partition) (OffsetAndMetadata. (+ 1 offset))}))

(defn seek
  ([consumer record]
   (seek consumer (.topic record) (.partition record) (.offset record)))
  ([consumer topic partition lastOffset]
   (Thread/sleep 1000)                                      ; Segundo a doc, esse tempo deve ser menor que o tempo de poll para não causar um rebalanceamento
   (.seek consumer (TopicPartition. topic partition) lastOffset)))

; Obs: para essa versão funcionar, precisa colocar o max-fetch em 1
(defn consume []
  (with-open [consumer (KafkaConsumer. consumer-configs)]
    (.subscribe consumer ["test-topic-001"])
    (loop [records []]
      (if-let [record (first records)]
        (if (consume-record record)
          (.commitSync consumer (build-commit-map record))
          (seek consumer record)))
      (recur (seq (.pool consumer (Duration/ofSeconds 1)))))))

(defn -main [& args]
  (consume))
