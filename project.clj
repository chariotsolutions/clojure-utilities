(defproject com.chariotsolutions.rlusk/utility "0.1.0-SNAPSHOT"
  :description "Make Ron's assorted Clojure inventions persistent and reusable"
  :url "https://github.com/chariotsolutions/clojure-utilities"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]]
  :profiles {:user {:resource-paths ["http://docs.hazelcast.org/docs/3.7/javadoc/"]}
             :dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]]}})
