(ns service.commons)

(defmacro spy
  "Helper function to extract values from functions"
  [body]
  `(let [result# ~body]
     (println "input " "==>" (quote ~body) "<==")
     (println "result" "==>" result# "<==")
     result#))
