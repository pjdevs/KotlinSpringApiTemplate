package org.example.demo.domain.usecases

import org.example.demo.domain.dtos.LoginRequestDto
import org.example.demo.domain.dtos.UserTokensDto
import org.example.demo.domain.ports.PasswordHasher
import org.example.demo.domain.ports.TokenService
import org.example.demo.domain.ports.UserRepository

sealed interface AuthResult {
    data class Success(val userTokensDto: UserTokensDto) : AuthResult
    data object InvalidPassword : AuthResult
    data object UserNotFound : AuthResult
}

class LoginUseCase(
    val userRepository: UserRepository,
    val tokenService: TokenService,
    val passwordHasher: PasswordHasher,
) {
    suspend fun execute(loginRequest: LoginRequestDto) : AuthResult {
        val user = userRepository.getUserByName(loginRequest.userName) ?: return AuthResult.UserNotFound

        if (!passwordHasher.isPasswordValid(loginRequest.password, user.passwordHash)) {
            return AuthResult.InvalidPassword
        }

        val tokens = tokenService.generateToken(user)

        return AuthResult.Success(
            UserTokensDto(
                tokens.accessToken,
                tokens.refreshToken,
                tokens.expiresIn,
                tokens.roles
            )
        )
    }
}