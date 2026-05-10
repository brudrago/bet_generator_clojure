(ns bet-generator-clojure.middleware
  (:require
    [bet-generator-clojure.logger]
    [cheshire.core :as json]
    [taoensso.timbre :as log]))

(defn wrap-json-content-type [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Content-Type"] "application/json"))))

(defn- log-request [request response duration]
  (let [data   {:event    "http_request"
                :method   (name (:request-method request))
                :uri      (:uri request)
                :status   (:status response)
                :duration duration}
        status (:status response)]
    (cond
      (>= status 500) (log/error data)
      (>= status 400) (log/warn data)
      :else           (log/info data))))

(defn wrap-request-logging [handler]
  (fn [request]
    (let [start    (System/currentTimeMillis)
          response (handler request)
          duration (- (System/currentTimeMillis) start)]
      (log-request request response duration)
      response)))

(defn wrap-exception [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception exception
        (log/error {:event  "unexpected_error"
                    :uri    (:uri request)
                    :error  (.getMessage exception)})
        {:status  500
         :headers {"Content-Type" "application/json"}
         :body    (json/generate-string {:error "Internal server error"})}))))
