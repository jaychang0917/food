package com.jaychang.food.controller

import com.jaychang.food.api.*
import com.jaychang.food.mockdata.COUNTRIES
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class SessionController : SessionsApi {
    override fun postSessions(sessionRequestBody: SessionRequestBody): ResponseEntity<Any> {
        if (sessionRequestBody.phoneNumber == "1" && sessionRequestBody.password == "a") {
            val user = SessionUser(sessionRequestBody.phoneNumber, "Jay Chang")
            val session = Session(accessToken = UUID.randomUUID().toString(), user = user)
            return ResponseEntity.ok(session)
        } else {
            val error = ApiError(
                code = "user_invalid_password",
                title = "Invalid password",
                detail = "The password doesn't match."
            )
            return ResponseEntity.badRequest().body(error)
        }
    }
}