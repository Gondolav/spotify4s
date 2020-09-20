# spotify4s

![Scala CI](https://github.com/Gondolav/spotify4s/workflows/Scala%20CI/badge.svg) [![Join the chat at https://gitter.im/spotify4s/community](https://badges.gitter.im/spotify4s/community.svg)](https://gitter.im/spotify4s/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

An intuitive and lightweight Scala library for the Spotify Web API.

- [Features](#features)
- [Installation](#installation)
- [Documentation](#documentation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Acknowledgements](#acknowledgements)
- [License](#license)

# Features

spotify4s supports all the features of the [Spotify Web API](https://developer.spotify.com/documentation/web-api/), with the sole exception of the Player API (in beta).

The whole API is exposed through the [Spotify class](https://gondolav.github.io/spotify4s/latest/api/com/github/gondolav/spotify4s/Spotify.html), modeling a Spotify API client. Every method returns an [Either](https://www.scala-lang.org/api/2.13.3/scala/util/Either.html) monad containing either the result or an error.

# Installation

spotify4s requires Scala 2.13. Add the following dependency to your SBT project:

```scala
libraryDependencies += "com.github.gondolav" %% "spotify4s" % "0.1.1"
```

# Documentation

The full API reference is available at [spotify4s documentation](https://gondolav.github.io/spotify4s/latest/api/com/github/gondolav/spotify4s/).

# Usage

To get started, install spotify4s and create an app on [Spotify](https://developer.spotify.com/dashboard/). Since all methods (and endpoints) require [user authorization](https://developer.spotify.com/documentation/general/guides/authorization-guide/), you will need the app's credentials (client id and client secret), generated upon registration.

The library supports two [authorization flows](https://gondolav.github.io/spotify4s/latest/api/com/github/gondolav/spotify4s/auth/index.html):

- **Client Credentials**: this flow makes it possible to authenticate your requests to the Spotify Web API and to obtain a higher rate limit than you would get with the Authorization code flow. However, only endpoints that do not access user information can be accessed;
- **Authorization Code**: this flow is suitable for long-running applications in which the user grants permission only once. It provides an access token that can be refreshed. It requires you to add a redirect URI to your app at the [dashboard](https://developer.spotify.com/dashboard/).

### Without user authentication (Client Credentials flow)

```scala
import com.github.gondolav.spotify4s._

val sp = Spotify(clientID, clientSecret)

sp.getCurrentUserProfile match {
  case Left(error) => // handle error
  case Right(user) => println(user.displayName) // for example
}
```

### With user authentication (Authorization Code flow)

```scala
import com.github.gondolav.spotify4s._

val sp = Spotify(clientID, clientSecret, redirectURI, scopes = List("user-read-email", "user-read-private"))

sp.getCurrentUserProfile match {
  case Left(error) => // handle error
  case Right(user) => println(user.displayName) // for example
}
```

# Contributing

Contributions are very welcome! A good place to start is the [Issues page](https://github.com/Gondolav/spotify4s/issues).

# Acknowledgements

spotify4s is partially inspired by [spotipy](https://github.com/plamere/spotipy). 

# License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/Gondolav/spotify4s/blob/master/LICENSE) file for details.
