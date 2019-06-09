(ns nukr.handler
  (:require [compojure.core :refer :all]
            [nukr.models.user :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] "Hello World")

  (POST "/create" {:keys [params]}
    (let [{:keys [name email]} params]
      (response (create-user name email))))

  (POST "/connect" {:keys [params]}
    (let [{:keys [guest host]} params]
      (response (connect-users guest host))))

  (POST "/connections" {:keys [params]}
    (let [{:keys [email]} params]
      (response (get-connections email))))

  (POST "/sub-connections" {:keys [params]}
    (let [{:keys [email]} params]
      (response (get-sub-connections email))))

  ; (POST "/suggestions" {:keys [params]}
  ;   (let [{:keys [email]} params]
  ;     (response (suggestions email))))

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
