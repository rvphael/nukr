(ns nukr.application.connection
  (:require 
    [nukr.domain.model.profile :as profile]))

(defn connect-profiles [guest host]
  (def old-guest-connections
    (profile/get-connections guest))
  (def old-host-connections
    (profile/get-connections host))
  (def new-guest-connections
    (cons host old-guest-connections))
  (def new-host-connections
    (cons guest old-host-connections))
  (profile/create-connection guest new-guest-connections)
  (profile/create-connection host new-host-connections))

(defn get-valid-profiles [profile-details]
  (filter #(= (:user/hidden %) false) profile-details))

(defn get-only-emails [profiles]
  (map #(select-keys % [:user/email]) profiles))

(defn build-sub-connections [email connections]
  (def sub-connections
    (for [email connections]
      (profile/get-connections email)))
  (def profile-details 
    (for [email (apply concat sub-connections)]
      (profile/get-profile-details email)))
  (def valid-profiles
    (get-valid-profiles profile-details))
  (def valid-emails
    (get-only-emails valid-profiles))
   (apply concat valid-emails))

(defn get-sub-connections [email]
  (def connections
    (profile/get-connections email))
  (def sub-connections
    (build-sub-connections email connections))
  (remove (fn [x]
            (= x email)) (apply concat sub-connections)))