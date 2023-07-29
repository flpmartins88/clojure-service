(ns service.ports.http-server
  (:require [io.pedestal.http.body-params :as http.body-params]
            [service.adapters.customer :as adapters.customer]
            [service.controllers.customer :as controllers.customer]))

(defn status
  [_request]
  {:status 200
   :body   "OK"})

(defn create-customer!
  [{body                       :json-params
    {:keys [datomic producer]} :components}]
  {:status 201
   :body   (-> body
               adapters.customer/wire->new-customer
               (controllers.customer/add! {:datomic datomic :producer producer}))})

(defn customer-by-id!
  [{{customer-id :id} :path-params
    {datomic :datomic producer :producer} :components}]
  {:status 200
   :body   (controllers.customer/find-by-id! (parse-uuid customer-id) {:datomic datomic :producer producer})})

(defn all-customers!
  [{{datomic :datomic} :components}]
  {:status 200
   :body   (controllers.customer/find-all {:datomic datomic})})

(def common-interceptors
  [(http.body-params/body-params)])

(def customer-routes
  #{["/customers"
     :post (conj common-interceptors create-customer!)
     :route-name :customers-create]
    ["/customers"
     :get (conj common-interceptors all-customers!)
     :route-name :customers-get]
    ["/customers/:id"
     :get (conj common-interceptors customer-by-id!)
     :route-name :get-customer-by-id]})

(def default-routes
  #{["/status"
     :get status
     :route-name :status-route]})
