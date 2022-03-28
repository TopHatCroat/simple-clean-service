package com.github.tophatcroat.plugins

import io.ktor.application.Application

fun Application.configureSecurity() {
//    install(Authentication) {
//            oauth("auth-oauth-google") {
//                urlProvider = { "http://localhost:8080/callback" }
//                providerLookup = {
//                    OAuthServerSettings.OAuth2ServerSettings(
//                        name = "google",
//                        authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
//                        accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
//                        requestMethod = HttpMethod.Post,
//                        clientId = System.getenv("GOOGLE_CLIENT_ID"),
//                        clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
//                        defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
//                    )
//                }
//                client = HttpClient(Apache)
//            }
//        }
//
//    routing {
//        authenticate("auth-oauth-google") {
//                    get("login") {
//                        call.respondRedirect("/callback")
//                    }
//
//                    get("/callback") {
//                        val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
//                        call.sessions.set(UserSession(principal?.accessToken.toString()))
//                        call.respondRedirect("/hello")
//                    }
//                }
//    }
}
