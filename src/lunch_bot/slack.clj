(ns lunch-bot.slack
  (use slack-rtm.core)
  (:require [clojure.string :as str]
            [lunch-bot.yelp :as yelp]))

(def rtm-conn (connect "YOUR_TOKEN_HERE!")) 
(def events-pub (:events-publication rtm-conn))
(def dispatcher (:dispatcher rtm-conn))


(send-event dispatcher {:type "message"})

"Reply directly to the user/channel who pinged us"
(defn direct-reply [m text]
  (send-event dispatcher {:type "message" :text text :user (:user m) :channel (:channel m)}))

(defn send-help [m]
  (direct-reply m "Just type `doris where's lunch?` or `doris where should I get lunch?` to get a recommendation"))

(defn find-specific [m]
  (direct-reply m "Something specific?"))

(defn find-category [m]
  (let [category (str/replace (str/lower-case (:text m)) "doris, i feel like " "")
        location (yelp/find-random category)]
    (if (nil? location) (direct-reply m (str "Well, oddly enough, I can't find any " category " around here!"))
    (direct-reply m (str "Well, if you want to eat " category " you should go to " (:name location) 
                        " at " (apply str (interleave (:address (:location location)) (repeat ", ")))))))) 

(defn random-lunch-spot [m]
  (let [location (yelp/find-random)]
    (println location)
    (direct-reply m (str "Go to " (:name location)
                         " at " (apply str (interleave (:address (:location location)) (repeat "\n")))
                         " They're rated " (:rating location) " stars."))))
(defn test-send [m]
  (send-event dispatcher m))

(defn test-template [m]
  (let [att (yelp/format-attachment (yelp/find-random) (:user m) (:channel m))]
    (clojure.pprint/pprint att)
   (test-send att)))

(defn im-receiver [m]
  (println m)
  (let [msg (str/lower-case (:text m))]
    (if (str/includes? msg "doris")
      (cond 
        (str/includes? msg "help") (do (println "Received help request.") (send-help m))
        (str/includes? msg "find") (do (println "Received find query.") (find-specific m))
        (str/includes? msg "i feel like") (do (println "Received category search query.") (find-category m))
        (str/includes? msg "test template") (do (println "Testing a template.") (test-template m))
        (and (str/includes? msg "lunch") 
             (or (str/includes? msg "what's for") 
                 (str/includes? msg "where"))) (do (println "Received general lunch query.") (random-lunch-spot m)))))

(sub-to-event events-pub :message im-receiver)
