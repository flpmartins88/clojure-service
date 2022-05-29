(ns service.wire.customer
  (:require [schema.core :as s]))

(def customer-type
  #{:person :company})

(s/defschema CustomerType (apply s/enum customer-type))

(def new-customer-skeleton {:name s/Str
                            :type s/Str})

(def customer-skeleton
  (assoc new-customer-skeleton :customer/id s/Uuid))

(s/defschema NewCustomer new-customer-skeleton)
(s/defschema Customer customer-skeleton)
