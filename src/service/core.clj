(ns service.core
  (:require [service.system :as system]
            [com.stuartsierra.component :as component]))

#_(defn -main [& args]
  (println "Initializing")
  (let [env    (or (-> args first keyword) :dev)
        system (system/new-system env)]
    #_(.addShutdownHook (Runtime/getRuntime)
                      (Thread. #^Runnable (do
                                 (println "Stopping")
                                 (component/stop system))))
    (component/start system)))
