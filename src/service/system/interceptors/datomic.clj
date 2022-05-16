(ns service.system.interceptors.datomic
  (:require [io.pedestal.interceptor :as interceptors]
            [service.system.protocols.datomic :as protocols.datomic]))

(defn db-interceptor
  "Assoc datomic client connection to request"
  [datomic]
  (interceptors/interceptor
    {:name  ::database-interceptor
     :enter (fn [context]
              (assoc-in context [:request :components :datomic]
                        (protocols.datomic/connect datomic)))}))
