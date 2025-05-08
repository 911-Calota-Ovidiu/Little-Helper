package org.example

import io.javalin.Javalin
import io.javalin.http.Context

class MockApiServer {
    private var app: Javalin? = null

    fun start(port: Int = 8080) {
        stop()
        app = Javalin.create().start(port)
    }

    fun stop() {
        app?.stop()
        app = null
    }

    fun addRoute(route: MockRoute) {
        println("Registering route: ${route.method} ${route.path}")
        app?.apply {
            when (route.method.uppercase()) {
                "GET" -> get(route.path) { ctx -> respond(ctx, route.response) }
                "POST" -> post(route.path) { ctx -> respond(ctx, route.response) }
                "PUT" -> put(route.path) { ctx -> respond(ctx, route.response) }
                "DELETE" -> delete(route.path) { ctx -> respond(ctx, route.response) }
            }
        }
    }

    private fun respond(ctx: Context, response: String) {
        println("Serving ${ctx.method()} ${ctx.path()}")
        ctx.contentType("application/json").result(response)
    }
}

data class MockRoute(val method: String, val path: String, val response: String)
