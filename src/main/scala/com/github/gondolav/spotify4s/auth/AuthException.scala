package com.github.gondolav.spotify4s.auth

class AuthException(msg: String, val authError: AuthError) extends Exception(msg)
