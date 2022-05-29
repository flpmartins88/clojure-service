(ns service.adapters.customer-test
  (:require [clojure.test :refer :all])
  (:require [service.adapters.customer :as adapters.customer]
            [matcher-combinators.test :refer [match?]]
            [schema.test]))

(schema.test/deftest wire->new-customer-test
  (testing "Given a company customer"
    (let [customer {:name "Google" :type :company}]
      (is (match? {:customer/name "Google" :customer/type :customer.type/company}
                  (adapters.customer/wire->new-customer customer))))))
