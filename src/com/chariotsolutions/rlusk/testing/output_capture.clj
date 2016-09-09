(ns com.chariotsolutions.rlusk.testing.output-capture
  "Testing support for capturing output to STDOUT and STDERR"
  (:import [java.io StringWriter PrintWriter]))

(defrecord TestOutput
    [print-writer string-writer]
  Object
  (toString [this]
    (.toString string-writer)))

(defn make-test-output
  "Create a TestOutput object we can use to capture output"
  []
  (let [string-writer (StringWriter. 4000)
        print-writer (PrintWriter. string-writer true)]
    (->TestOutput print-writer string-writer)))

(defmacro capture-test-output
  "Capture STDOUT and STDERR for testing output routines

   Use as:

        (deftest foo
          (testing \"foo\"
            (capture-test-output
              [err-str out-str]
              (println \"Out\")
              (binding [*out* *err*]
                (println \"Err\"))
              (println \"Out\")
              :evaluation
              (is (= err-str \"Err\n\"))
              (is (= out-str \"Out\n\")))))

"
  [err-out-names & body]
  (let [test-setup# (take-while (complement keyword?) body)
        test-eval# (rest (drop-while (complement keyword?) body))]
    `(let [err-output# (make-test-output)
           out-output# (make-test-output)]
       (binding [*err* (:print-writer err-output#)
                 *out* (:print-writer out-output#)]
         ~@test-setup#
         (let [~(first err-out-names) (str err-output#)
               ~(second err-out-names) (str out-output#)]
           ~@test-eval#)))))

