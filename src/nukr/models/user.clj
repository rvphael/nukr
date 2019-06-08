(ns nukr.models.user
  (:require [com.stuartsierra.mapgraph :as mg]
            [nano-id.core :refer [nano-id]]))

(def db (atom (mg/new-db)))

(defn create-user [name email]
  (swap! db mg/add-id-attr :user/id :user/email)
  (swap! db mg/add
    {:user:id (nano-id 5)
     :user/name name
     :user/email email
     :user/hidden false
     :user/connections (vector)}))

(defn get-current-connections [email]
  (def attributes
    (get @db [:user/email email]))
    (get-in attributes [:user/connections]))

(defn create-connection [email new-connection]
  (swap! db mg/add
    {:user/email email
     :user/connections new-connection}))

(defn connect-users [guest host]
  (def old-guest-connections
    (get-current-connections guest))
  (def old-host-connections
    (get-current-connections host))
  (def new-guest-connections
    (cons host old-guest-connections))
  (def new-host-connections
    (cons guest old-host-connections))
  (create-connection guest new-guest-connections)
  (create-connection host new-host-connections))

(defn show [email]
  (get @db [:user/email email]))