(ns service.db.datomic.customer
  (:require [schema.core :as s]
            [service.schema.customer :as schema.customer]
            [datomic.client.api :as d]))

(defn- customer->datomic
  [customer])

(s/defn find-by-id :- schema.customer/Customer
  [customer-id :- s/Uuid
   datomic :- s/Any]
  (->> (d/q '{:find  [(pull ?customer [* {:customer/type [:db/ident]}])]
              :in    [$ ?customer-id]
              :where [[?customer :customer/id ?customer-id]]}
            (d/db datomic) customer-id)
       (map first)
       (map (fn [customer] (assoc customer :customer/type (get-in customer [:customer/type :db/ident]))))
       (map (fn [customer] (dissoc customer :db/id)))
       first))

(s/defn persist! :- schema.customer/Customer
  [customer :- schema.customer/NewCustomer
   datomic :- s/Any]
  (let [customer-id (random-uuid)
        customer-with-id (assoc customer :customer/id customer-id)]
    (d/transact datomic {:tx-data [customer-with-id]})
    (find-by-id customer-id datomic)))

(s/defn find-all! :- [schema.customer/Customer]
  [datomic :- s/Any]
  (->> (d/q '{:find  [(pull ?customer [* {:customer/type [:db/ident]}])]
             :where [[?customer :customer/id _]]}
           (d/db datomic))
       (map first)
       (map (fn [customer] (assoc customer :customer/type (get-in customer [:customer/type :db/ident]))))
       (map (fn [customer] (dissoc customer :db/id)))))
