(ns com.chariotsolutions.rlusk.err-output
  "Definitions for writing easily to STDERR")

(defmacro with-stderr
  "Write default output to *err* instead of *out*"
  [& body]
  `(binding [*out* *err*]
     ~@body))

(defn- extract-fname*
  "Return (as a string) the unqualified function name

   (extract-fname* `clojure.pprint/pprint) => \"pprint\"
   (extract-fname* `printf) => \"printf\" "
  [symname]
  (let [symstr (str symname)]
    (if (re-find #"/" symstr)
      (second (clojure.string/split symstr #"/"))
      symstr)))

(defmacro wrap-stderr
  "Wrap an output call with the `with-stderr` macro"
  ([e-name stdout-fn]
   `(defn ~e-name
      "Direct output to *err*"
      [& args#]
      (with-stderr
        (apply ~stdout-fn args#))))
  ([stdout-fn]
   (let [fn-name# (extract-fname* stdout-fn)
         e-name# (symbol (str "err-" fn-name#))]
     `(defn ~e-name#
        "Direct output to *err*"
        [& args#]
        (with-stderr
          (apply ~stdout-fn args#))))))

(wrap-stderr println)
(wrap-stderr eprintf)
(wrap-stderr clojure.pprint/pprint)






