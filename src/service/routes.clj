(ns service.routes
  (:require [service.ports.http-server :as http-server]))

(def routes
  (into http-server/default-routes
        http-server/customer-routes))
