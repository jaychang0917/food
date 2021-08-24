package com.jaychang.food.api

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * 
 * @param phoneNumber 
 * @param password 
 */
data class SessionRequestBody(

    @field:JsonProperty("phone_number", required = true) val phoneNumber: kotlin.String,

    @field:JsonProperty("password", required = true) val password: kotlin.String
) {

}

