(ns bet-generator-clojure.generator
  (:require [bet-generator-clojure.bet-options :as opt]))

(defn valid-bet-type? [bet-type]
  contains? opt/bet-options bet-type)

(defn draw [bet-type]
  (if-not (valid-bet-type? bet-type)
    (throw
      (ex-info
        "Invalid bet type"
        {:bet-type bet-type
         :available-types
         (keys opt/bet-options)}))
  (let [{:keys [count min max]} (get opt/bet-options bet-type)]
    {:type    bet-type
     :numbers (->> (range min (inc max))
                   shuffle
                   (take count)
                   sort
                   vec)})))
