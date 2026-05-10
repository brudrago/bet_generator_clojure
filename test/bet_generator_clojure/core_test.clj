(ns bet-generator-clojure.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [bet-generator-clojure.core :refer [app]]))

(defn parse-body [response]
  (json/parse-string (:body response) true))

(deftest health-test
  (let [response (app (mock/request :get "/health"))]

    (testing "returns status 200"
      (is (= 200 (:status response))))

    (testing "returns json content-type"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))

    (testing "returns ok status"
      (is (= "ok" (:status (parse-body response)))))))

(deftest bets-megasena-test
  (let [response (app (mock/request :get "/bets/user-123/megasena"))]

    (testing "returns status 200"
      (is (= 200 (:status response))))

    (testing "returns json content-type"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))

    (testing "returns megasena type"
      (is (= "megasena" (:type (parse-body response)))))

    (testing "returns 6 numbers"
      (is (= 6 (count (:numbers (parse-body response))))))

    (testing "returns user-id"
      (is (= "user-123" (:user-id (parse-body response)))))))

(deftest bets-quina-test
  (let [response (app (mock/request :get "/bets/user-123/quina"))]

    (testing "returns status 200"
      (is (= 200 (:status response))))

    (testing "returns json content-type"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))

    (testing "returns quina type"
      (is (= "quina" (:type (parse-body response)))))

    (testing "returns 5 numbers"
      (is (= 5 (count (:numbers (parse-body response))))))

    (testing "returns user-id"
      (is (= "user-123" (:user-id (parse-body response)))))))

(deftest bets-invalid-game-test
  (let [response (app (mock/request :get "/bets/user-123/invalid"))]

    (testing "returns status 400"
      (is (= 400 (:status response))))

    (testing "returns json content-type"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))))

(deftest not-found-test
  (let [response (app (mock/request :get "/unknown"))]

    (testing "returns status 404"
      (is (= 404 (:status response))))

    (testing "returns json content-type"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))))
