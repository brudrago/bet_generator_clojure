(defproject bet_generator_clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [ring/ring-core "1.12.2"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [compojure "1.7.1"]
                 [cheshire "5.13.0"]
                 [com.taoensso/timbre "6.5.0"]]
  :main bet-generator-clojure.core
  :profiles {:dev {:dependencies [[ring/ring-mock "0.4.0"]]}}
  :repl-options {:init-ns bet-generator-clojure.core})
