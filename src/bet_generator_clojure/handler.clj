(ns bet-generator-clojure.handler
  (:require
    [bet-generator-clojure.generator :as generator]
    [bet-generator-clojure.logger]
    [taoensso.timbre :as log]
    [cheshire.core :as json]))

(defn json-response [status body]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defn handle [user-id game]
  (try
    (let [result (generator/draw (keyword game))]
      (log/info {:event   "bet_generated"
                 :user-id user-id
                 :game    game
                 :status  200})
      (json-response 200 (assoc result :user-id user-id)))
    (catch clojure.lang.ExceptionInfo exception
      (log/warn {:event   "invalid_bet_type"
                 :user-id user-id
                 :game    game
                 :status  400
                 :error   (.getMessage exception)})
      (json-response 400 {:error   (.getMessage exception)
                          :details (ex-data exception)}))))