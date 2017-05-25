# lunch-bot

A clojure app designed as a POC for an integration between Slack and Yelp for randomized lunch suggestions.

## Config
  Look in slack.clj for the placeholder for your slack bot's id
  Look in yelp.clj for the placeholders for your API credentials and location
  Update the location in Slack.clj or, ideally, update the code so that this is dynamic per user and persisted in a database location of your choice. 

## Usage

Setup: 
  1. Be sure to review the Config section before attempting to run

Debug: 
  1. Run `lein repl` from the command prompt in the project's directory
  2. Type `(ns lunch-bot) to switch to the lunch-bot namespace
  3. Type `(require `lunch-bot.slack)` to connect to your slack instance as "Doris"
  4. Interact with bot via slack (see slack.clj for recognized commands)

Normal Run: 
  1. Run `lein run` 

Deployment: 
  1. Run `lein jar`
  2. Copy output jar file from target/ to wherever makes most sense for you (local dir, server, etc) 
  3. Run `java -jar <output-jar-name>.jar`


## ToDo

  1. Spin up database of some sort and store location per user so that Westlake isn't hard coded
  2. Pull Yelp connection information out to env variables
  3. Deploy properly somewhere
  4. Thread yelp callouts
  5. Unit tests

## License

Copyright © 2016 Brick

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
