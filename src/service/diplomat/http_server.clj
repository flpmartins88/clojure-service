(ns service.diplomat.http-server
  (:require [service.controllers.customer :as controllers.customer]
            [service.schema.customer :as schema.customer]))

(defn status
  [_request]
  {:status 200
   :body   "OK"})

(defn create-customer!
  [{{datomic :datomic} :components}]
  {:status 200
   :body   (controllers.customer/add! #:customer{:name "Felipe"
                                                 :type schema.customer/type-person}
                                      {:datomic datomic})})

(defn all-customers!
  [{{datomic :datomic} :components}]
  {:status 200
   :body   (controllers.customer/find-all! {:datomic datomic})})

(def customer-routes
  #{["/customers"
     :post create-customer!
     :route-name :customers-create]
    ["/customers"
     :get all-customers!
     :route-name :customers-get]})

(def default-routes
  #{["/status"
     :get status
     :route-name :status-route]})
