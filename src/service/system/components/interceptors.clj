(ns service.system.components.interceptors
  (:require [com.stuartsierra.component :as component]
            [service.system.interceptors.datomic :as interceptors.datomic]
            [service.system.interceptors.kafka-producer :as interceptors.kafka-producer]))

; Here, I create an interceptor to add it to interceptors list in pedestal
; :common will hold all common interceptors to this project
(defrecord Interceptors
  [service-map datomic kafka-producer]
  component/Lifecycle

  (start
    [component]
    (assoc component :common [(interceptors.datomic/db-interceptor datomic)
                              (interceptors.kafka-producer/kafka-producer-interceptor kafka-producer)]))

  (stop
    [component]
    (assoc component :common nil)))

(defn new-interceptors
  []
  (map->Interceptors {}))
