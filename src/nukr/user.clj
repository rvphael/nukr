(ns nukr.user
  (:require [com.stuartsierra.mapgraph :as mg]))

(def db (atom (mg/new-db)))

(defn create [name email password]
  (swap! db mg/add-id-attr :user/email)
  (swap! db mg/add 
                  {:user/name name
                   :user/email email
                   :user/password password}))

(defn show [email]
  (get @db [:user/email email]))
