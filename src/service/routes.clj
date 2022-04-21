(ns service.routes
  (:require [service.diplomat.http-server :as http-server]))

(def routes
  #{["/status"
     :get http-server/status
     :route-name :status-route]})
