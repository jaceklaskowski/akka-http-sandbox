# Getting started

Start `./activator` in the current directory and, while in activator shell, execute `reStart` task, i.e.

    $ ./activator
    [akka-http-sandbox]> ~reStart

You should see the following logs:

    [info] Application akka-http-sandbox not yet started
    [info] Starting application akka-http-sandbox in the background ...
    akka-http-sandbox Starting pl.japila.akka.http.Hello.main()
    [success] Total time: 0 s, completed Feb 7, 2016 9:21:36 PM
    1. Waiting for source changes... (press enter to interrupt)
    akka-http-sandbox Hello, Akka-HTTP world!

# Requests

    http -v http://localhost:8080/actor Content-Type:application/json name=jacek
