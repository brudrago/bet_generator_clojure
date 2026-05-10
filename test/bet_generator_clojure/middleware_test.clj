(ns bet-generator-clojure.middleware-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [bet-generator-clojure.middleware :as middleware]))

(deftest wrap-json-content-type-test
  (let [handler  (middleware/wrap-json-content-type (fn [_] {:status 200 :body "ok"}))
        response (handler (mock/request :get "/any"))]

    (testing "adds Content-Type application/json"
      (is (= "application/json" (get-in response [:headers "Content-Type"]))))))

(deftest wrap-exception-test
  (testing "passes through successful response"
    (let [handler  (middleware/wrap-exception (fn [_] {:status 200 :body "ok"}))
          response (handler (mock/request :get "/any"))]
      (is (= 200 (:status response)))))

  (testing "returns 500 on uncaught exception"
    (let [handler  (middleware/wrap-exception (fn [_] (throw (RuntimeException. "boom"))))
          response (handler (mock/request :get "/any"))]
      (is (= 500 (:status response))))))

(deftest wrap-request-logging-test
  (testing "passes through the response unchanged"
    (let [handler  (middleware/wrap-request-logging (fn [_] {:status 200 :body "ok"}))
          response (handler (mock/request :get "/any"))]
      (is (= 200 (:status response)))
      (is (= "ok" (:body response))))))
