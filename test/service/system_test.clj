(ns service.system-test
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :refer [response-for]]
            [com.stuartsierra.component :as component]
            [clojure.test :refer :all]
            [service.routes]
            [service.system]
            [schema.test]))

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

(schema.test/deftest status-test
  (with-system [sut (service.system/new-system :test)]
    (testing "When send a request to get status")
    (let [service (service-fn sut)
          {:keys [status body]} (response-for service :get (url-for :status-route))]
      (testing "Then the result"
        (testing "status code should be 200"
          (is (= 200 status)))

        (testing "body should be OK"
          (is (= "OK" body)))))))

(comment
  (status-test))
