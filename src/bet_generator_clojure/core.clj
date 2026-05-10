(ns bet-generator-clojure.core
  (:require
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [ring.adapter.jetty :refer [run-jetty]]
    [bet-generator-clojure.handler :as handler])
  (:gen-class))

(defroutes app-routes

           ;; Healthcheck
           (GET "/health" []
                {:status  200
                 :headers {"Content-Type" "application/json"}
                 :body    "{\"status\":\"ok\"}"})

           (GET "/bets/:game" [game]
                (handler/handle game))

           ;; 404
           (route/not-found
             {:status  404
              :headers {"Content-Type" "application/json"}
              :body    "{\"error\":\"Route not found\"}"}))

(defn -main
  [& _args]

  (println "Server running on port 3000")

  (run-jetty
    app-routes
    {:port 3000
     :join? false}))