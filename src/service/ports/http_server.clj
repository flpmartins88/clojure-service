(ns service.ports.http-server
  (:require [service.controllers.customer :as controllers.customer]
            [service.schema.customer :as schema.customer]
            [service.commons :refer [tap]]))

(defn status
  [_request]
  {:status 200
   :body   "OK"})

(defn create-customer!
  [{{datomic :datomic} :components :as request}]
  (tap (-> request :body))
  {:status 201
   :body   (controllers.customer/add! #:customer{:name "Felipe"
                                                 :type schema.customer/type-person}
                                      {:datomic datomic})})

(defn customer-by-id!
  [{{customer-id :id} :path-params
    {datomic :datomic} :components}]
  {:status 200
   :body   (controllers.customer/find-by-id! (parse-uuid customer-id) {:datomic datomic})})

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
     :route-name :customers-get]
    ["/customers/:id"
     :get customer-by-id!
     :route-name :get-customer-by-id]})

(def default-routes
  #{["/status"
     :get status
     :route-name :status-route]})
