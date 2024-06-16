(defproject clojure-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  ;:repositories [["cognitect-dev-tools" {:url      "https://dev-tools.cognitect.com/maven/releases/"
  ;                                       :username :env
  ;                                       :password :env}]]

  :dependencies [[org.clojure/clojure "1.11.1"]

                 [com.datomic/local "1.0.277"]

                 [prismatic/schema "1.2.0"]

                 [com.stuartsierra/component "1.1.0"]
                 [com.stuartsierra/component.repl "1.0.0"]

                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.route "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [org.slf4j/slf4j-simple "1.7.36"]

                 [org.apache.kafka/kafka-clients "3.1.0"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.13.2"]

                 [nubank/matcher-combinators "3.5.0"]]
  :repl-options {:init-ns service.core})
