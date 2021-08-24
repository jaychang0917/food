package com.jaychang.food.controller

import com.jaychang.food.api.ApiError
import com.jaychang.food.api.CountriesApi
import com.jaychang.food.api.Country
import com.jaychang.food.api.UsersApi
import com.jaychang.food.mockdata.COUNTRIES
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class UserController : UsersApi {
    override fun getUsersUserId(userId: String): ResponseEntity<Any> {
        if (userId == "1") {
            return ResponseEntity.ok().build()
        } else {
            val error = ApiError(
                code = "user_not_exist",
                title = "User not exist"
            )
            return ResponseEntity.badRequest().body(error)
        }
    }
}