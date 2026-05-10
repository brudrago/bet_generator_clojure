(ns bet-generator-clojure.generator-test
  (:require [clojure.test :refer :all]
            [bet-generator-clojure.generator :as generator]))

(deftest draw-megasena-test
  (let [result (generator/draw :megasena)]

    (testing "returns correct type"
      (is (= :megasena (:type result))))

    (testing "returns 6 numbers"
      (is (= 6 (count (:numbers result)))))

    (testing "numbers are within range"
      (is (every? #(and (>= % 1) (<= % 60)) (:numbers result))))

    (testing "numbers are unique"
      (is (= (count (:numbers result)) (count (set (:numbers result))))))

    (testing "numbers are sorted"
      (is (= (:numbers result) (sort (:numbers result)))))))

(deftest draw-quina-test
  (let [result (generator/draw :quina)]

    (testing "returns correct type"
      (is (= :quina (:type result))))

    (testing "returns 5 numbers"
      (is (= 5 (count (:numbers result)))))

    (testing "numbers are within range"
      (is (every? #(and (>= % 1) (<= % 80)) (:numbers result))))

    (testing "numbers are unique"
      (is (= (count (:numbers result)) (count (set (:numbers result))))))

    (testing "numbers are sorted"
      (is (= (:numbers result) (sort (:numbers result)))))))

(deftest draw-invalid-type-test
  (testing "throws exception for invalid bet type"
    (is (thrown? clojure.lang.ExceptionInfo
                 (generator/draw :invalid)))))
