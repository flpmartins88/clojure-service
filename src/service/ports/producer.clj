(ns service.ports.producer
  (:require [schema.core :as s]
            [service.schema.customer :as schema.customer]
            [service.system.protocols.kafka-producer :as protocols.kafka-producer]
            [service.system.types :as types]
            [service.commons :refer [spy]]))

(s/defn customer-created
  [customer :- schema.customer/Customer
   producer :- types/KafkaProducer]
  (let [customer-id (str (:customer/id customer))]
    (protocols.kafka-producer/produce! producer "CUSTOMERS" customer-id customer)))
