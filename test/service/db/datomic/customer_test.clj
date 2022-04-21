(ns service.db.datomic.customer-test
  (:require [clojure.test :refer :all]
            [schema.test]
            [service.db.datomic.customer :as datomic.customer]
            [matcher-combinators.test :refer [match?]]))

(schema.test/deftest persist!-test
  (testing "Given a customer"
    (let [customer {:customer/name "Felipe"
                    :customer/type :person}
          datomic  {}]
      (testing "When save a customer"
        (let [saved-customer (datomic.customer/persist! customer datomic)]
          (testing "Then the same customer should be returned"
            (is (match? customer
                        saved-customer))))))))

(comment
  (schema.core/set-fn-validation! true)
  (persist!-test))
