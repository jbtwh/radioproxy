(defproject radioproxy "1.0"
  :description "Demo Clojure web app"
  :url "http://clojure-getting-started.herokuapp.com"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [environ "1.0.0"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "radioproxy-standalone.jar"
  :profiles {:production {:env {:production true}}})
