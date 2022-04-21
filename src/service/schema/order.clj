(ns service.schema.order
  (:require [schema.core :as s]
            [service.schema.item :as schema.item]
            [service.schema.customer :as schema.customer]))

(def order-skeleton #:order{:customer schema.customer/Customer
                            :items    [schema.item/Item]})

(s/defschema Order order-skeleton)
