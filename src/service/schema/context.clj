(ns service.schema.context
  (:require [schema.core :as s]
            [service.system.types :as types]))

(s/defschema Context
  {(s/optional-key :datomic)  types/Datomic
   (s/optional-key :producer) types/KafkaProducer})
