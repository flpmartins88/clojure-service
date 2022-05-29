(ns service.aux.commons
  (:require [clojure.test :refer :all]
            [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [datomic.client.api :as d]
            [service.system.components.datomic :as components.datomic]))

(def url-for (route/url-for-routes
               (route/expand-routes service.routes/routes)))

(defn service-fn
  [system]
  (get-in system [:pedestal :service ::http/service-fn]))

(defmacro with-system
  [[bound-var binding-expr] & body]
  `(let [~bound-var (component/start ~binding-expr)]
     (try
       ~@body
       (finally
         (component/stop ~bound-var)))))

(defmacro with-database
  "Get a connection from components"
  [system [datomic db] & body]
  (let [client (gensym "client")]
    `(let [~client (get-in ~system [:datomic :client])
           ~datomic (d/connect ~client components.datomic/db-params)]
       ~@body)))
