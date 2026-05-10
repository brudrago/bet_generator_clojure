(ns bet-generator-clojure.handler
  (:require
    [bet-generator-clojure.generator :as generator]
    [cheshire.core :as json]))

(defn json-response [status body]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defn handle [game]
  (try
    (json-response 200 (generator/draw (keyword game)))
    (catch clojure.lang.ExceptionInfo exception
      (json-response
        400
        {:error (.getMessage exception)
         :details (ex-data exception)}))))