(ns bet-generator-clojure.logger
  (:require
    [taoensso.timbre :as timbre]
    [cheshire.core :as json]))

(timbre/merge-config!
  {:appenders
   {:println
    {:enabled? true
     :fn
     (fn [{:keys [level ?err msg_ timestamp_]}]

       (println
         (json/generate-string
           {:timestamp (force timestamp_)
            :level (name level)
            :message (force msg_)
            :error (when ?err (.getMessage ?err))})))}}})