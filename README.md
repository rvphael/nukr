# Nukr - Social media connections

The problem statement is as follows:
You are tasked with making Nukr, a new social media product by Nu Everything S / A. The initial step is to create a prototype service that provides a REST API where we can simulate connections between people, and explore how we would offer new connection suggestions.
These are the features required:

- Be able to add a new profile;
- Be able to tag two profiles as connected;
- Be able to generate a list of new connection suggestions for a certain profile, taking the stance that the more connections to profile have with another profile's connections, the better ranked the suggestion should be;
- Some profiles can, for privacy reasons, opt to be hidden from the connection suggestions.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

**The API has 4 primary endpoints that can be accessed as soon as the application is started.**

`1 [POST] - http://localhost:3000/add-profile`

Make a POST request at this route and pass a JSON like this:

```    
{
    "name": "Bruce",
    "email: "bruce@wayne.com"
}
```

The profile `Bruce` will be added on the **Nukr** and a JSON like this will be returned:

```
{
    "[:user/email \"bruce@wayne.com\"]": {
        "user/name": "Bruce",
        "user/email": "bruce@wayne.com",
        "user/hidden": false,
        "user/connections": []
}
```

`2 [PUT] - http://localhost:3000/connect-profiles`

Make a PUT request at this route and pass a JSON like this:

```
{

    "host": "bruce@wayne.com",
    "guest": "jason@todd.com"
}
```

Where `HOST` is your profile and `GUEST` is the profile that you want to connect to. The connection will be made to the two profiles.
And it will be returned something like:

```
"[:user/email \"bruce@wayne.com\"]": {
    "user/name": "Bruce",
    "user/email": "bruce@wayne.com",
    "user/hidden": false,
    "user/connections": [
        "jason@todd.com"
    ]
},
"[:user/email \"jason@todd.com\"]": {
    "user/name": "Jason",
    "user/email": "jason@todd.com",
    "user/hidden": false,
    "user/connections": [
        "bruce@wayne.com"
    ]
}
```

`3 [POST] - http://localhost:3000/connection-suggestions`

Make a POST request at this route and pass a JSON like this:

```
{
    "email": "bruce@wayne.com"
}
```

Where email is referenced to the email of your profile. Suggestions are created from the common connections of your own connections, for example: Suppose you are profile the "A" and are connected to profile "B" and "C", and the two are connected to profile "D", then "D" becomes a connection suggestion. And the more connections your own connections have in common, the better ranked are the suggestions for you.

`4 [PUT] - http://localhost:3000/change-hidden-status`

It is possible that other profiles will receive you as a connection suggestion. To change your privacy status, make a PUT request like this:

```
{
    "email": "bruce@wayne.com",
    "new-status": true
}
```

This will changed the status `hidden` false to true

Note: The other profiles can also change the status so they do not appear in your suggestions

## Running

To install the dependencies, run:

```
lein deps
```

To start a web server for the application, run:

```
lein ring server
```

## Running tests

```
lein test
```