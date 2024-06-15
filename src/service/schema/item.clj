(ns service.schema.item
  (:require [schema.core :as s]))

(def item-skeleton
  #:item{:name     s/Str
         :quantity s/Int
         :amount   s/Int})

(s/defschema Item
  item-skeleton)
