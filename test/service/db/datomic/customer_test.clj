(ns service.db.datomic.customer-test
  (:require [clojure.test :refer :all]
            [schema.test]
            [service.db.datomic.customer :as datomic.customer]
            [matcher-combinators.test :refer [match?]]))

(defmacro with-database
  "Get a connection from components"
  [system [datomic db] & body]
  (let [client (gensym "client")]
    `(let [~client (get-in ~system [:datomic :client])
           ~datomic (d/connect ~client components.datomic/db-params)]
       ~@body)))

; Create a version that does not need to run all system
#_(defmacro with-database
  [])

(schema.test/deftest persist!-test
  (testing "Given a customer"

    (let [customer {:customer/name "Felipe"
                    :customer/type :person}
          datomic  {}]
      (testing "When save a customer"
        #_(let [saved-customer (datomic.customer/persist! customer datomic)]
          (testing "Then the same customer should be returned"
            (is (match? customer
                        saved-customer))))))))

(comment
  (schema.core/set-fn-validation! true)
  (persist!-test))
