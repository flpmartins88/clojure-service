(ns service.customer-test
  (:require [clojure.test :refer :all]
            [io.pedestal.test :refer [response-for]]
            [matcher-combinators.test :refer [match?]]
            [clojure.test :refer :all]
            [service.routes]
            [service.system]
            [schema.test]
            [service.aux.commons :refer :all]
            [datomic.client.api :as d]))

(schema.test/deftest create-customer-test
  (with-system [sut (service.system/new-system :test)]
    (testing "When send a request to create a customer"
      (let [{:keys [status body]} (request! :post
                                            (url-for :customers-create)
                                            sut
                                            :body "{\"name\": \"Felipe\", \"type\": \"person\"}")]
        (testing "Then the result"
          (testing "status code should be 200"
            (is (= 201 status)))

          (testing "body should be OK"
            (is (match? #:customer{:id uuid?, :name "Felipe", :type :customer.type/person}
                        (clojure.edn/read-string body)))))))))

(schema.test/deftest get-customer-by-id
  (with-system [sut (service.system/new-system :test)]
    (with-database sut [datomic db]
      (testing "Given a customer in database"
        (let [customer #:customer{:id (random-uuid) :name "Pedro" :type :customer.type/person}]
          (d/transact datomic {:tx-data [customer]})
          (testing "When send a request to get a customer by ID"
            (let [service (service-fn sut)
                  {:keys [status body]} (response-for service :get (clojure.string/replace (url-for :get-customer-by-id)
                                                                                           ":id"
                                                                                           (str (:customer/id customer))))]
              (testing "Then the result"
                (testing "status should be 200"
                  (is (= 200 status)))

                (testing "body should return the customer"
                  (is (match? customer
                              (clojure.edn/read-string body))))))))))))

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
  (get-customers-test)
  (get-customer-by-id))
