(ns service.system.types
  (:require [service.system.protocols.kafka-producer :as protocols.kafka-producer]
            [schema.core :as s]
            [datomic.client.api.protocols :as protocols.datomic]))

(def KafkaProducer
  (s/protocol protocols.kafka-producer/KafkaProducer))

(def Datomic
  (s/protocol protocols.datomic/Connection))
