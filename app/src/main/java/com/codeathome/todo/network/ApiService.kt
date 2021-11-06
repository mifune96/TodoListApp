package com.codeathome.todo.network

import com.codeathome.todo.model.Item
import com.codeathome.todo.model.Todo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("todos")
    fun getAllTodo(): Call<Todo>

    @FormUrlEncoded
    @POST("todos")
    fun postTodo(
        @Field("name") name: String
    ): Call<Todo>

    @DELETE("todos/{id}")
    fun deletTodo(
        @Path("id") id: Int
    ): Call<Todo>

    @FormUrlEncoded
    @PUT("todos/{id}")
    fun updateTodo(
        @Path("id") id: Int,
        @Field("name") name: String
    ): Call<Todo>

    @FormUrlEncoded
    @POST("items")
    fun postTodoItem(
        @Field("name") name: String,
        @Field("TodoId") id: Int
    ): Call<Item>

    @FormUrlEncoded
    @PUT("items/{id}")
    fun updateTodoItem(
        @Path("id") id: Int,
        @Field("name") name: String
    ): Call<Item>

    @DELETE("items/{id}")
    fun deleteTodoItem(
        @Path("id") id: Int
    ): Call<Item>


}