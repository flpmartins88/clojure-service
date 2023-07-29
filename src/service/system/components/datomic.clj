(ns service.system.components.datomic
  (:require [com.stuartsierra.component :as component]
            [service.system.protocols.datomic :as protocols.datomic]
            [datomic.client.api :as d]
            [datomic.dev-local]))

(def client-params {:server-type :dev-local
                    :storage-dir :mem
                    :system "dev"})

(def db-params {:db-name "orders"})

(defn test?
  [environment]
  (= :test environment))

; This connection in parameter is the same connection that will be added in service map
; The component used in start/stop functions, are the map returned by the assoc function
(defrecord Datomic
  [environment database-schemas client]
  component/Lifecycle

  (start [component]
    (if client
      component
      (let [client (d/client client-params)]
        (d/create-database client db-params)
        (d/transact (d/connect client db-params) {:tx-data database-schemas})
        (assoc component :client client))))

  (stop [component]
    (when (test? environment)
      (d/delete-database client db-params))
    (assoc component :client nil))

  protocols.datomic/Datomic

  (connect [this]
    (d/connect (-> this :client) db-params)))

(comment
  (def client (d/client client-params))
  (d/create-database client db-params)
  (def datomic (d/connect client db-params))

  (d/transact datomic {:tx-data service.db.datomic.schemas/customer-schema})
  (d/transact datomic {:tx-data service.db.datomic.schemas/item-schema})

  (datomic.dev-local/release-db (merge client-params db-params)))

; Here we can pass more parameters
; Dependencies are injected by components' lib
(defn new-datomic
  []
  (map->Datomic {}))
