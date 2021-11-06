package com.codeathome.todo.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Items")
    val items: List<Item>,
    @SerializedName("name")
    val name: String
)