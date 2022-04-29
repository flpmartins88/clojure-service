(ns service.components.datomic
  (:require [com.stuartsierra.component :as component]
            [datomic.client.api :as d]
            [datomic.dev-local]
            [service.commons :refer [tap]]))

(def client-params {:server-type :dev-local
                    :storage-dir :mem
                    :system "dev"})

(def db-params {:db-name "orders"})

; This connection in parameter is the same connection that will be added in service map
; The component used in start/stop functions, are the map returned by the assoc function
(defrecord Datomic
  [client]
  component/Lifecycle

  (start [component]
    (if client
      component
      (let [client (d/client client-params)]
        (d/create-database client db-params)
        (assoc component :client client))))

  (stop [component]
    (assoc component :client nil)))

(comment
  (def client (d/client client-params))
  (d/create-database client db-params)
  (def datomic (d/connect client db-params))

  (datomic.dev-local/release-db (merge client-params db-params)))

; Here we can pass more parameters
; Dependencies are injected by components' lib
(defn new-datomic
  []
  (map->Datomic {}))
