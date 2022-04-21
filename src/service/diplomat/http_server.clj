(ns service.diplomat.http-server)

(defn status
  [_request]
  {:status 200
   :body   "OK"})
