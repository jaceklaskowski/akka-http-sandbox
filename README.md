# Getting started

Start `sbt` and, while on sbt shell, execute `reStart`.

    $ sbt
    [akka-http-sandbox]> ~reStart

# Requests

    http -v http://localhost:8080/actor Content-Type:application/json name=jacek
