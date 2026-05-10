(ns bet-generator-clojure.core
  (:require
    [compojure.core :refer [GET defroutes]]
    [compojure.route :as route]
    [ring.adapter.jetty :refer [run-jetty]]
    [bet-generator-clojure.handler :as handler]
    [bet-generator-clojure.middleware :as middleware])
  (:gen-class))

(defroutes app-routes

           (GET "/health" []
                {:status 200
                 :body   "{\"status\":\"ok\"}"})

           (GET "/bets/:game" [game]
                (handler/handle game))

           (route/not-found
             {:status 404
              :body   "{\"error\":\"Route not found\"}"}))

(def app
  (-> app-routes
      middleware/wrap-exception
      middleware/wrap-json-content-type
      middleware/wrap-request-logging))

(defn -main
  [& _args]
  (println "Server running on port 3000")
  (run-jetty app {:port 3000 :join? false}))