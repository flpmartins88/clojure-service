(ns service.db.datomic.schemas)

(def customer-schema
  [{:db/ident       :customer/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :customer/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :customer/type
    :db/valueType   :db.type/ref
    :db/cardinality :db.cardinality/one}
   {:db/ident :customer.type/person}
   {:db/ident :customer.type/company}])

(def item-schema
  [])

(def order-schema
  [])
