(ns nukr.handler
  (:require [compojure.core :refer :all]
            [nukr.db.user-db :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] "Hello World")

  (POST "/create" {:keys [params]}
    (let [{:keys [name email hidden]} params]
      (response (create name email hidden))))

  (POST "/show" {:keys [params]}
    (let [{:keys [email]} params]
      (response (show email))))

  (POST "/to-connect" {:keys [params]}
    (let [{:keys [guest host]} params]
      (response (to-connect guest host))))

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
