(ns service.schema.customer
  (:require [schema.core :as s]))

(def type-person
  :customer.type/person)

(def type-company
  :customer.type/company)

(def customer-type
  #{type-person
    type-company})

(s/defschema CustomerType
  (apply s/enum customer-type))

(def new-customer-skeleton
  #:customer {:name s/Str
              :type CustomerType})

(def customer-skeleton
  (assoc new-customer-skeleton :customer/id s/Uuid))

(s/defschema NewCustomer
  new-customer-skeleton)

(s/defschema Customer
  customer-skeleton)
