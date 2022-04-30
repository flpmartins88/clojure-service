(ns service.customer-test
  (:require [clojure.test :refer :all]
            [service.system-test :refer [with-system with-database service-fn url-for]]
            [io.pedestal.test :refer [response-for]]
            [matcher-combinators.test :refer [match?]]
            [clojure.test :refer :all]
            [service.routes]
            [service.system]
            [schema.test]
            [datomic.client.api :as d]))

(schema.test/deftest create-customer-test
  (with-system [sut (service.system/new-system :test)]
    (testing "When send a request to create a customer")
    (let [service (service-fn sut)
          {:keys [status body]} (response-for service :post (url-for :customers-create))]
      (testing "Then the result"
        (testing "status code should be 200"
          (is (= 200 status)))

        (testing "body should be OK"
          (is (match? #:customer{:id uuid?, :name "Felipe", :type :customer.type/person}
                      (clojure.edn/read-string body))))))))

(schema.test/deftest get-customers-test
  (with-system [sut (service.system/new-system :test)]
    (with-database sut [datomic db]
      (testing "Given some customers"
        (let [customer-1 #:customer{:id (random-uuid), :name "João", :type :customer.type/person}
              customer-2 #:customer{:id (random-uuid), :name "Maria", :type :customer.type/person}]
          (d/transact datomic {:tx-data [customer-1 customer-2]})
          (testing "When send a request to get all customers"
            (let [service (service-fn sut)
                  {:keys [status body]} (response-for service :get (url-for :customers-get))]
              (testing "Then the result"
                (testing "status code should be 200"
                  (is (= 200 status)))

                (testing "body should be OK"
                  (is (match? [customer-1 customer-2]
                              (clojure.edn/read-string body))))))))))))

(comment
  (create-customer-test)
  (get-customers-test))