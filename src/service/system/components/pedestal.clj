(ns service.system.components.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defn test?
  [{:keys [env]}]
  (= :test env))

(defn- add-interceptors
  "Helper to add interceptors to interceptor list of pedestal

  Receives a pedestal's service map with default-interceptors set and add common interceptors
  from interceptors component"
  [service-map interceptors]
  (update service-map ::http/interceptors into (:common interceptors)))

(defrecord Pedestal
  [service-map interceptors service]
  component/Lifecycle

  (start [this]
    (if service
      this
      (cond-> service-map
              true                      http/default-interceptors
              true                      (add-interceptors interceptors)
              true                      http/create-server
              (not (test? service-map)) http/start
              true                      ((partial assoc this :service)))))

  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal
  [env]
  (map->Pedestal {:env env}))
