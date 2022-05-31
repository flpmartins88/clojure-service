(ns service.aux.commons
  (:require [clojure.test :refer :all]
            [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [datomic.client.api :as d]
            [io.pedestal.http :as http]
            [io.pedestal.test :refer [response-for]]
            [service.routes]
            [service.system.components.datomic :as components.datomic]
            [service.commons :refer [spy]]))

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

(def default-headers
  {"Content-Type" "application/json"})

(defn request!
  ([verb url system & {:keys [headers body]}]
   (response-for (service-fn system)
                 verb
                 url
                 :headers (merge default-headers headers)
                 :body body)))
