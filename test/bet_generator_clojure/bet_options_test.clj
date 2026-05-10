(ns bet-generator-clojure.bet-options-test
  (:require [clojure.test :refer :all]
            [bet-generator-clojure.bet-options :as opt]))

(deftest bet-options-test
  (testing "bet-options contains megasena and quina"
    (is (contains? opt/bet-options :megasena))
    (is (contains? opt/bet-options :quina)))

  (testing "megasena config is correct"
    (is (= 6 (get-in opt/bet-options [:megasena :count])))
    (is (= 1 (get-in opt/bet-options [:megasena :min])))
    (is (= 60 (get-in opt/bet-options [:megasena :max]))))

  (testing "quina config is correct"
    (is (= 5 (get-in opt/bet-options [:quina :count])))
    (is (= 1 (get-in opt/bet-options [:quina :min])))
    (is (= 80 (get-in opt/bet-options [:quina :max])))))
