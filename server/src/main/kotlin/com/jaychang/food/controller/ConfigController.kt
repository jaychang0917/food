package com.jaychang.food.controller

import com.jaychang.food.api.CountriesApi
import com.jaychang.food.api.Country
import com.jaychang.food.mockdata.COUNTRIES
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class ConfigController : CountriesApi {
    override fun getCountries(): ResponseEntity<List<Country>> {
        return ResponseEntity.ok().body(COUNTRIES.values.toList())
    }
}