(ns service.system.protocols.kafka)

; In some place I need to connect in kafka
; Maybe this will reference the Kafka Component
; or kafka producer

(defprotocol Kafka
  (produce! [this topic value] [this topic key value]))
