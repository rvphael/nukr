(ns nukr.controller.user
  (:require
    [ring.middleware.json :as json]
    [ring.util.response :refer [response]]))

(defn create [name email password]
  (response name))
  