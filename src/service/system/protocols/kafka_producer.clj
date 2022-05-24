(ns service.system.protocols.kafka-producer)

; In some place I need to connect in kafka
; Maybe this will reference the Kafka Component
; or kafka producer

; The 'this' parameter MUST BE the component where this
; defprotocol is declared
(defprotocol KafkaProducer
  (produce! [this topic key value]))
