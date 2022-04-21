(ns service.routes)

(defn status
  [request]
  {:status 200
   :body   "OK"})

(def routes
  #{["/status"
     :get status
     :route-name :status-route]})
