(defproject lunch-bot "0.1.0-SNAPSHOT"
  :description "A Yelp lunch bot for Slack"
  :url "n/a"
  :main lunch-bot.core
  :uberjar-name "lunch-bot.jar"
  :profiles {:uberjar {:aot :all}}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [gws/clj-yelp "0.3.4"]
                 [slack-rtm "0.1.3"]
                 [org.clojure/data.json "0.2.6"]])
