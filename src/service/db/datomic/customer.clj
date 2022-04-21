(ns service.db.datomic.customer
  (:require [schema.core :as s]
            [service.schema.customer :as schema.customer]))

(s/defn persist!
  "Just a fake function to serve a placeholder"
  [customer :- schema.customer/Customer
   _datomic :- s/Any]
  customer)
