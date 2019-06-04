(ns nukr.handler
  (:require [compojure.core :refer :all]
            [nukr.controller.user :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (POST "/create" {:keys [params]}
    (let [{:keys [name email password]} params]
      (response (create name email password))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
