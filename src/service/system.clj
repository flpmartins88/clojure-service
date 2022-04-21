(ns service.system
  (:require [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
            [io.pedestal.http :as http]
            [service.components.pedestal :as components.pedestal]
            [service.routes]))

(defn new-system
  [env]
  (component/system-map :service-map {:env          env
                                      ::http/routes service.routes/routes
                                      ::http/type   :jetty
                                      ::http/port   8890
                                      ::http/join?  false}

                        :pedestal (component/using (components.pedestal/new-pedestal) [:service-map])))
