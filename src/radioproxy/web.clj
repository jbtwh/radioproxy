(ns radioproxy.web
    (:gen-class)
    (:import [java.io InputStream InputStreamReader BufferedReader]
      [java.net URL HttpURLConnection Proxy InetSocketAddress Proxy$Type]
      [javax.naming InitialContext]

      )
    (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
      [compojure.handler :refer [site]]
      [compojure.route :as route]
      [ring.adapter.jetty :as jetty]
      [environ.core :refer [env]]
      [clojure.java.io :refer [file output-stream input-stream resource]]
      )
    )


(defn get-url
      [url]
      (let [conn (.openConnection (new URL url))]
           (.setRequestMethod  conn "GET")
           (.setRequestProperty conn "Connection" "close")
           (.setUseCaches conn false)
           (System/setProperty "http.keepAlive" "false")
           (System/setProperty "java.net.preferIPv4Stack" "true")
           (.getInputStream conn)))

;;http://78.140.251.2:8101/rr_320
;;http://www.google.com


(defn splash
      [request]
      (println (:r (:params request)))
      (let [r (:r (:params request))]
           {:status  200
            :headers {"Content-Type" "audio/mpeg"}
            :body    (get-url r)})
      )

(defn notfound
      []
      {:status  404
       :headers {"Content-Type" "text/plain"}
       :body "Hello from Heroku"})

(defroutes app
           (GET "/splash" request
             (splash request))
           (ANY "*" request
             (notfound)))


;;(future (start-server :port 7888 :bind "0.0.0.0"))
;;(defonce server (start-server :port 7888 :bind "0.0.0.0"))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))