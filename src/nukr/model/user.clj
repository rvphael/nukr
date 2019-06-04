(ns nukr.model.user
  (:require
    nukr.controller.user :refer :all]
    [ring.middleware.json :as json]
    [ring.util.response :refer [response]]))
  
(defn create [name email password]
  (response name))
    