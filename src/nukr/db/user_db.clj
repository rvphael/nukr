(ns nukr.db.user-db
  (:require [com.stuartsierra.mapgraph :as mg]))

(def db (atom (mg/new-db)))

(defn create [name email hidden]
  (swap! db mg/add-id-attr :user/email)
  (swap! db mg/add 
    {:user/name name
      :user/email email
      :user/hidden hidden}))

(defn to-connect [guest host]
  (swap! db mg/add
    {:user/email host
    :user/connections #{{:s guest}}}
   {:user/email guest
    :user/connections #{{:s host}}}))

(defn show [email]
  (get @db [:user/email email]))