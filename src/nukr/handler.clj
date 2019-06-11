(ns nukr.handler
  (:require [compojure.core :refer :all]
            [nukr.models.user :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] "Hello World")

  (POST "/add-profile" {:keys [params]}
    (let [{:keys [name email]} params]
      (response (add-profile name email))))

  (PUT "/connect-profiles" {:keys [params]}
    (let [{:keys [guest host]} params]
      (response (connect-profiles guest host))))

  (POST "/connection-suggestions" {:keys [params]}
    (let [{:keys [email]} params]
      (response (connection-suggestions email))))

  (PUT "/change-hidden-status" {:keys [params]}
    (let [{:keys [email new-status]} params]
      (response (change-hidden-status email new-status))))

  (POST "/connections" {:keys [params]}
    (let [{:keys [email]} params]
      (response (get-connections email))))

  (POST "/sub-connections" {:keys [params]}
    (let [{:keys [email]} params]
      (response (get-sub-connections email))))

  (POST "/show" {:keys [params]}
    (let [{:keys [email]} params]
      (response (get-connections email))))

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
