(ns service.controllers.customer
  (:require [schema.core :as s]
            [service.schema.context :as schema.context]
            [service.schema.customer :as schema.customer]
            [service.db.datomic.customer :as datomic.customer]
            [datomic.client.api :as d]))

(s/defn add! :- schema.customer/Customer
  [customer :- schema.customer/NewCustomer
   {:keys [datomic]} :- schema.context/Context]
  (datomic.customer/persist! customer datomic))

(s/defn find-by-id! :- schema.customer/Customer
  [customer-id :- s/Uuid
   {:keys [datomic]} :- schema.context/Context]
  (datomic.customer/find-by-id customer-id (d/db datomic)))

(s/defn find-all! :- [schema.customer/Customer]
  [{:keys [datomic]} :- schema.context/Context]
  (datomic.customer/find-all! datomic))
