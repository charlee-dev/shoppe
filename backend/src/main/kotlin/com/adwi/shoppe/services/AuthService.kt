package com.adwi.shoppe.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.adwi.shoppe.models.User
import com.adwi.shoppe.models.UserInput
import com.adwi.shoppe.models.UserResponse
import com.adwi.shoppe.repository.UserRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.ApplicationCall
import org.litote.kmongo.MongoOperator
import java.nio.charset.StandardCharsets
import java.util.*

class AuthService(private val repository: UserRepository) {

    private val secret: String = "secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun signIn(userInput: UserInput): UserResponse? {
        val user = repository.getUserByEmail(userInput.email) ?: error("No such user by that email")
        if (!BCrypt.verifyer()
                .verify(
                    userInput.password.toByteArray(Charsets.UTF_8),
                    user.hashedPass
                ).verified
        ) {
            error("Password incorrect")
        }
        val token = signAccessToken(MongoOperator.id.toString())
        return UserResponse(token, user)
    }

    fun signUp(userInput: UserInput): UserResponse? {
        val hashedPassword = BCrypt.withDefaults().hash(10, userInput.password.toByteArray(StandardCharsets.UTF_8))
        val id = UUID.randomUUID().toString()
        val emailUser = repository.getUserByEmail(userInput.email)
        if (emailUser != null) {
            error("Email already in use")
        }
        val newUser = repository.add(
            User(
                id = id,
                email = userInput.email,
                hashedPass = hashedPassword,
            )
        )
        val token = signAccessToken(newUser.id)
        return UserResponse(token, newUser)
    }

    private fun signAccessToken(id: String): String {
        return JWT.create()
            .withIssuer(id)
            .withClaim("userId", id)
            .sign(algorithm)
    }

    fun verifyToken(call: ApplicationCall): User? {
        return try {
            val authHeader = call.request.headers["Authorization"] ?: ""
            val token = authHeader.split("Bearer ").last()
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            return User(id = userId, email = "", hashedPass = ByteArray(0))
        } catch (e: Exception) {
            null
        }
    }
}