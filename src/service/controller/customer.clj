(ns service.controller.customer
  (:require [schema.core :as s]
            [service.schema.context :as schema.context]
            [service.schema.customer :as schema.customer]
            [service.db.datomic.customer :as datomic.customer]))

(s/defn add! :- schema.customer/Customer
  [customer :- schema.customer/Customer
   {:keys [datomic]} :- schema.context/Context]
  (datomic.customer/persist! customer datomic))
