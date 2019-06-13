(ns nukr.handler
  (:require 
    [compojure.core :refer :all]
    [nukr.domain.model.profile :as profile]
    [nukr.application.connection :as conn]
    [nukr.application.suggestion :as sugg]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [ring.middleware.json :as json]
    [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] "Hello World")

  (POST "/add-profile" {:keys [params]}
    (let [{:keys [name email]} params]
      (response (profile/add-profile name email))))

  (PUT "/connect-profiles" {:keys [params]}
    (let [{:keys [guest host]} params]
      (response (conn/connect-profiles guest host))))

  (POST "/connection-suggestions" {:keys [params]}
    (let [{:keys [email]} params]
      (response (sugg/connection-suggestions email))))

  (PUT "/change-hidden-status" {:keys [params]}
    (let [{:keys [email new-status]} params]
      (response (profile/change-hidden-status email new-status))))

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
