(ns nukr.domain.model.profile
  (:require [com.stuartsierra.mapgraph :as mg]))

(def db (atom (mg/new-db)))

(defn add-profile [name email]
  (try (swap! db mg/add-id-attr :user/email)
  (swap! db mg/add
         {:user/name name
          :user/email email
          :user/hidden false
          :user/connections (vector)})
  (catch Throwable t t)))

(defn create-connection [email new-connection]
  (try (swap! db mg/add
         {:user/email email
          :user/connections new-connection})
  (catch Throwable t t)))

(defn get-connections [email]
  (def attributes
    (get @db [:user/email email]))
  (get-in attributes [:user/connections]))

(defn get-profile-details [email]
  (get @db [:user/email email]))

(defn change-hidden-status [email new-status]
  (try (swap! db
        mg/add
        {:user/email email
          :user/hidden new-status})
  (catch Throwable t t)))

(defn get-hidden-status [email]
  (def attributes
    (get @db [:user/email email]))
  (def status
    (get-in attributes [:user/hidden]))
    status)