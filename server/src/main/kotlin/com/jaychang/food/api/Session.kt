package com.jaychang.food.api

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import com.jaychang.food.api.SessionUser
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * A user session
 * @param accessToken 
 * @param user 
 */
data class Session(

    @field:JsonProperty("access_token", required = true) val accessToken: kotlin.String,

    @field:Valid
    @field:JsonProperty("user", required = true) val user: SessionUser
) {

}

