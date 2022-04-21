(ns service.schema.customer
  (:require [schema.core :as s]))

(def customer-type #{:person :company})
(s/defschema CustomerType (apply s/enum customer-type))

(def customer-skeleton #:customer{:name s/Str
                                  :type CustomerType})

(s/defschema Customer customer-skeleton)
