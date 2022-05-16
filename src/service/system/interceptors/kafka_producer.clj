(ns service.system.interceptors.kafka-producer
  (:require [io.pedestal.interceptor :as interceptors]))

(defn kafka-producer-interceptor
  "Assoc Kafka Producer to request"
  [kafka]
  (interceptors/interceptor
    {:name  ::kafka-producer-interceptor
     :enter (fn [context]
              (assoc-in context [:request :components :producer]
                        (-> kafka :producer)))}))
