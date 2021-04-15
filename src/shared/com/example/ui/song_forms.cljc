(ns com.example.ui.song-forms
  (:require
    [com.example.model.song :as song]
    [com.fulcrologic.rad.form-options :as fo]
    [com.fulcrologic.rad.form :as form]
    [clojure.string :as str]
    [taoensso.timbre :as log]
    [com.example.model :as model]
    [com.example.model.account :as account]
    [com.example.model.timezone :as timezone]
    [com.example.ui.address-forms :refer [AddressForm]]
    [com.example.ui.file-forms :refer [FileForm]]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.mutations :refer [defmutation]]
    [com.fulcrologic.fulcro.algorithms.form-state :as fs]
    [com.fulcrologic.rad.semantic-ui-options :as suo]
    #?(:clj  [com.fulcrologic.fulcro.dom-server :as dom :refer [div label input]]
       :cljs [com.fulcrologic.fulcro.dom :as dom :refer [div label input]])
    [com.fulcrologic.rad.control :as control]
    [com.fulcrologic.rad.report :as report]
    [com.fulcrologic.rad.report-options :as ro]


    )

  )

(comment
  ;;Process of adding a form:
  ;; Create a model under com/example/model (e.g. song.cljc).
  ;; Create a form under com/example/ui (e.g. song_forms.cljc)
  ;; Follow minimal templates of the above to implement.
  ;; In root UI (ui.cljc), place forms in desired location (follow example there as well).
  ;; If there are any resolvers in model that are custom, add to parser.clj.
  ;; add model attributes to model.cljc in all-attributes vector (in top level directory, next to ui.cljc).

  ;; Optional (though highly encouraged to see results): add seeds to seed.clj (see song example) and then
  ;; add new data using the newly created seed function in development.clj
  )

(form/defsc-form SongForm [this props]
                 {fo/id           song/id
                  fo/attributes   [song/name song/artist song/album]
                  fo/cancel-route ["landing-page"]
                  fo/route-prefix "song"
                  fo/title        "Edit Song"
                  fo/layout       [[:song/name]
                                   [:song/artist :song/album]]})


(report/defsc-report SongList [this props]
                     {ro/title                              "All songs"
                      ro/source-attribute                   :song/all-songs
                      ro/row-pk                             song/id
                      ro/columns                            [song/id song/name song/artist song/album]
                      ro/column-headings                    {:song/id "Song Id"}


                      ;; No control layout...we don't actually let the user control it

                      ro/run-on-mount?                      true
                      ro/route                              "songs"})