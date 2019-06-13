(ns nukr.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [nukr.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Welcome to Nukr!"))))

  (testing "add profile"
    (let [response (app (mock/request :post "/add-profile"))]
      (is (= (:status response) 200))))

  (testing "connect profiles"
    (let [response (app (mock/request :put "/connect-profiles"))]
      (is (= (:status response) 200))))

  (testing "connection suggestions"
    (let [response (app (mock/request :post "/connection-suggestions"))]
      (is (= (:status response) 200))))

  (testing "change hidden status"
    (let [response (app (mock/request :put "/change-hidden-status"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
