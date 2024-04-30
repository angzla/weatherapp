package com.example.weather.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.weather.data.cityItem

class CityViewModel: ViewModel() {
    private var _cityList =
        mutableStateListOf<cityItem>()

    fun addToCityList(cityItem: cityItem) {
        _cityList.add(cityItem)
    }


    fun removeItem(cityItem: cityItem) {
        _cityList.remove(cityItem)
    }

    fun clearAllItems() {
        _cityList.clear()
    }

    fun getAllitems(): List<cityItem> {
        return _cityList
    }

    fun getCity(cityItem: cityItem): String{
        return cityItem.city
        }
}