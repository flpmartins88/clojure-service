(ns service.controllers.customer
  (:require [schema.core :as s]
            [service.schema.context :as schema.context]
            [service.schema.customer :as schema.customer]
            [service.db.datomic.customer :as datomic.customer]
            [service.ports.producer :as ports.producer]
            [datomic.client.api :as d]
            [service.commons :refer [spy]]))

(s/defn add! :- schema.customer/Customer
  [customer :- schema.customer/NewCustomer
   {:keys [datomic producer]} :- schema.context/Context]
  (spy datomic)
  (doto (datomic.customer/persist! customer datomic)
    (ports.producer/customer-created producer)))

(s/defn find-by-id! :- schema.customer/Customer
  [customer-id :- s/Uuid
   {:keys [datomic]} :- schema.context/Context]
  (datomic.customer/find-by-id customer-id (d/db datomic)))

(s/defn find-all! :- [schema.customer/Customer]
  [{:keys [datomic]} :- schema.context/Context]
  (datomic.customer/find-all! datomic))
