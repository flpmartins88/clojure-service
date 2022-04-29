(ns service.components.interceptors
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor :as interceptors]
            [datomic.client.api :as d]
            [service.components.datomic :as components.datomic]
            [service.commons :refer [tap]]))

(defn db-interceptor
  "Assoc datomic client connection to request"
  [datomic]
  {:name  ::database-interceptor
   :enter (fn [context] (assoc-in context [:request :components :datomic] (d/connect (:client datomic) components.datomic/db-params)))})

; Here, I create an interceptor to add it to interceptors list in pedestal
; :common will hold all common interceptors to this project
(defrecord Interceptors
  [service-map datomic]
  component/Lifecycle

  (start
    [component]
    (assoc component :common [(interceptors/interceptor (db-interceptor datomic))]))

  (stop
    [component]
    (assoc component :common nil)))

(defn new-interceptors
  []
  (map->Interceptors {}))
