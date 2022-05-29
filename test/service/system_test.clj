(ns service.system-test
  (:require [io.pedestal.test :refer [response-for]]
            [clojure.test :refer :all]
            [service.routes]
            [service.system]
            [schema.test]
            [service.aux.commons :refer :all]))

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
