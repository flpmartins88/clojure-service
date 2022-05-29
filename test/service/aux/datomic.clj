(ns service.aux.datomic
  (:require [clojure.test :refer :all]
            [schema.core :as s]
            [service.schema.customer :as schema.customer]
            [service.aux.commons :refer :all]
            [datomic.client.api :as d]))

(s/defn add-customer!
  [customer :- schema.customer/Customer
   system]
  (with-database system [datomic db]
    (d/transact datomic {:tx-data [customer]})))
