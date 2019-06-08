(defproject nukr "0.1.0-SNAPSHOT"
  :description "Social media connections"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [com.stuartsierra/mapgraph "0.2.1"]
                 [nano-id "0.9.3"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler nukr.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
