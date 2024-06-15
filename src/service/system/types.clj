(ns service.system.types
  (:require [service.system.protocols.kafka-producer :as protocols.kafka-producer]
            [schema.core :as s]
            [datomic.client.api.protocols :as protocols.datomic])
  (:import (java.time ZonedDateTime)))

(def KafkaProducer
  (s/protocol protocols.kafka-producer/KafkaProducer))

(def Datomic
  (s/protocol protocols.datomic/Connection))

(def Date
  (s/protocol ZonedDateTime))

(s/defn teste
  [date :- Date]
  (println date))

(s/set-fn-validation! true)
(teste (ZonedDateTime/now))
