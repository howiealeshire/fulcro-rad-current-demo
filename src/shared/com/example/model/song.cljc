(ns com.example.model.song
  (:refer-clojure :exclude [name])
  (:require
    #?@(:clj
        [[com.wsscode.pathom.connect :as pc :refer [defmutation]]
         [com.example.model.authorization :as exauth]
         [com.example.components.database-queries :as queries]]
        :cljs
        [[com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]])
    [clojure.string :as str]
    [com.example.model.timezone :as timezone]
    [com.fulcrologic.rad.form :as form]
    [com.fulcrologic.rad.form-options :as fo]
    [com.fulcrologic.rad.report :as report]
    [com.fulcrologic.rad.attributes :as attr :refer [defattr]]
    [com.fulcrologic.rad.attributes-options :as ao]
    [com.fulcrologic.rad.authorization :as auth]
    [com.fulcrologic.rad.middleware.save-middleware :as save-middleware]
    [com.fulcrologic.rad.blob :as blob]
    [taoensso.timbre :as log]
    [com.fulcrologic.fulcro.ui-state-machines :as uism]
    [com.fulcrologic.rad.type-support.date-time :as datetime]
    )
  )


(defattr id :song/id :uuid
         {ao/identity? true
          ;; NOTE: These are spelled out so we don't have to have either on classpath, which allows
          ;; independent experimentation. In a normal project you'd use ns aliasing.
          ao/schema    :production
           })


(defattr name :song/name :string
         {ao/required? true
          ao/identities #{:song/id}
          ao/schema :production
          })

(defattr artist :song/artist :string
         {ao/identities #{:song/id}
          ao/schema :production
          })

(defattr album :song/album :string
         {ao/identities #{:song/id}
          ao/schema :production
          })


(defattr all-songs :song/all-songs :ref
         {ao/target     :song/id
          ao/pc-output  [{:song/all-songs [:song/id]}]
          ao/pc-resolve (fn [{:keys [query-params] :as env} _]
                          #?(:clj
                             {:song/all-songs (queries/get-all-songs env query-params)}))})



(def attributes [id name artist album all-songs])

#?(:clj
   (def resolvers []))
