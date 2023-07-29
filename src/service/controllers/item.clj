(ns service.controllers.item
  (:require [schema.core :as s]
            [service.schema.context :as schema.context]))

(s/defn save!
  [item
   context :- schema.context/Context])

(s/defn find-by-id
  [])

(s/defn find-all
  [])
