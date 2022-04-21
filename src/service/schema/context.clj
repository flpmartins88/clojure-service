(ns service.schema.context
  (:require [schema.core :as s]))

(s/defschema Context
  {(s/optional-key :datomic) s/Any})
