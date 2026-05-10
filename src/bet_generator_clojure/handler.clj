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

(defn handle [game]
  (let [start (System/currentTimeMillis)]
    (try
      (let [result   (generator/draw (keyword game))
            duration (- (System/currentTimeMillis) start)]
        (log/info {:event    "bet_generated"
                   :game     game
                   :status   200
                   :duration duration})
        (json-response 200 result))
      (catch clojure.lang.ExceptionInfo exception
        (log/warn {:event    "invalid_bet_type"
                   :game     game
                   :status   400
                   :duration (- (System/currentTimeMillis) start)
                   :error    (.getMessage exception)})
        (json-response 400 {:error   (.getMessage exception)
                            :details (ex-data exception)}))
      (catch Exception exception
        (log/error {:event    "unexpected_error"
                    :game     game
                    :status   500
                    :duration (- (System/currentTimeMillis) start)
                    :error    (.getMessage exception)})
        (json-response 500 {:error "Internal server error"})))))