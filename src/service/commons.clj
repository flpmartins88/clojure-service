(ns service.commons)

(defmacro tap
  "Helper function to extract values from functions"
  [body]
  `(let [result# ~body]
     (println "tap   " "==>" (quote ~body) "<==")
     (println "result" "==>" result# "<==")
     result#))
