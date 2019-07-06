(ns giggin.fb.auth
  (:require ["firebase/app" :as firebase]
            [giggin.fb.db :refer [db-save!]]
            [giggin.state :as state]))

;;var provider = new firebase.auth.GoogleAuthProvider ();
(defn sign-in-with-google
  []
  (let [provider (firebase/auth.GoogleAuthProvider.)]
    ;;firebase.auth () .signInWithPopup (provider)
    (.signInWithPopup (firebase/auth) provider)))

(defn sign-out
  []
  ;;firebase.auth () .signOut ()
  (.signOut (firebase/auth)))

;;firebase.auth().onAuthStateChanged(function(user)
(defn on-auth-state-change
  []
  (.onAuthStateChanged 
   (firebase/auth) 
   (fn 
     [user] 
     (if user
       (let [uid (.-uid user)
             display-name (.-displayName user)
             photo-url (.-photoURL user)
             email (.-email user)]
         (do
           (reset! state/user {:photo-url photo-url
                               :display-name display-name
                               :email email})
           (db-save!
            ["users" uid "profile"]
            #js {:photo-url photo-url
                 :display-name display-name
                 :email email})))
       (reset! state/user nil)))))