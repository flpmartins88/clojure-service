(ns service.system
  (:require [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
            [io.pedestal.http :as http]
            [service.system.components.pedestal :as components.pedestal]
            [service.system.components.datomic :as components.datomic]
            [service.system.components.interceptors :as components.interceptors]
            [service.routes]
            [service.db.datomic.schemas :as datomic.schemas]
            [service.system.components.kafka-producer :as components.kafka-producer]))

(def database-schemas
  (concat datomic.schemas/customer-schema
          datomic.schemas/order-schema
          datomic.schemas/item-schema))

(defn new-system
  [env]
  (component/system-map
    :environment      env
    :service-map      {:env          env
                       ::http/routes service.routes/routes
                       ::http/type   :jetty
                       ::http/port   8890
                       ::http/join?  false}

    :kafka-producer   (components.kafka-producer/new-kafka-producer)

    :pedestal         (component/using (components.pedestal/new-pedestal env) [:service-map :interceptors])
    :datomic          (component/using (components.datomic/new-datomic) [:environment :database-schemas])

    :interceptors     (component/using (components.interceptors/new-interceptors) [:datomic :kafka-producer])
    :database-schemas database-schemas))
