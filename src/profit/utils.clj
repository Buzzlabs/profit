(ns profit.utils
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [environ.core :refer [env]]))


(def config-file-path (format "%s/.profit/config.edn" (env :home)))
(def profile (or (keyword (System/getenv "PROFIT_PROFILE")) :light))
(def config-file (io/file config-file-path))

(def config (if config-file
              (aero/read-config config-file {:profile profile})
              {:start-color  "\033[37;1;45m\033[1m"}))

(def start-color (:start-color config))


(defn pprint-str [x]
  (let [str (with-out-str (pprint x))]
    (subs str 0 (dec (count str)))))

(defn color-str [text]
  (let [restore-color "\033[0m"]
    (str start-color text restore-color)))

(defn truncate-str [s max-length]
  (let [len (count s)]
    (if (<= len max-length)
      s
      (str (subs s 0 (- max-length 3)) "..."))))
