(ns service.system.protocols.datomic)

(defprotocol Datomic
  (connect [this]))
