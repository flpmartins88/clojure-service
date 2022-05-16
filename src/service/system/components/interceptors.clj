(ns service.system.components.interceptors
  (:require [com.stuartsierra.component :as component]
            [service.system.interceptors.datomic :as interceptors.datomic]))


; Here, I create an interceptor to add it to interceptors list in pedestal
; :common will hold all common interceptors to this project
(defrecord Interceptors
  [service-map datomic]
  component/Lifecycle

  (start
    [component]
    (assoc component :common [(interceptors.datomic/db-interceptor datomic)]))

  (stop
    [component]
    (assoc component :common nil)))

(defn new-interceptors
  []
  (map->Interceptors {}))
