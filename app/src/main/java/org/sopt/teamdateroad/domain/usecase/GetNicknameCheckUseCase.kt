package org.sopt.teamdateroad.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import org.sopt.teamdateroad.domain.repository.AuthRepository

@Singleton
class GetNicknameCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String): Result<Int> = authRepository.getNicknameCheck(name)
}
