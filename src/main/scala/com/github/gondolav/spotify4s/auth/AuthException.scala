package com.github.gondolav.spotify4s.auth

/**
 * An exception issued from an authorization attempt.
 *
 * @param message   the detail message
 * @param authError the error received
 */
class AuthException(message: String, val authError: AuthError) extends Exception(message)
