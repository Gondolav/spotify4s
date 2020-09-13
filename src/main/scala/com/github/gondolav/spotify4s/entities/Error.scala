package com.github.gondolav.spotify4s.entities

import upickle.default._

/**
 * Stores the information returned by unsuccessful responses.
 *
 * @param status  the HTTP status code that is also returned in the response header. For further information, see
 *                [[https://developer.spotify.com/documentation/web-api/#response-status-codes Response Status Codes]]
 * @param message a short description of the cause of the error
 */
case class ErrorInfo(status: Int, message: String)

object ErrorInfo {
  private[spotify4s] implicit val rw: ReadWriter[ErrorInfo] = macroRW
}

/**
 * Stores the information returned by unsuccessful responses.
 *
 * @param error an object storing the detailed information about the error
 */
case class Error(error: ErrorInfo)

object Error {
  private[spotify4s] implicit val rw: ReadWriter[Error] = macroRW
}
