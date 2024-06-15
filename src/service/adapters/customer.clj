(ns service.adapters.customer
  (:require [schema.core :as s]
            [service.schema.customer :as schema.customer]
            [service.wire.customer :as wire.customer]))

(s/defn wire->new-customer :- schema.customer/NewCustomer
  [{customer-name :name
    customer-type :type} :- wire.customer/NewCustomer]
  (let [customer-type (keyword "customer.type" (name customer-type))]
    #:customer {:name customer-name
                :type customer-type}))
