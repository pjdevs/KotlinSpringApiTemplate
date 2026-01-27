package org.example.demo.api

import org.example.demo.domain.mocks.UnknownCurrentUserInfo
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.infra.persistence.JpaVideoReactionRepository
import org.example.demo.infra.persistence.JpaVideoRepository
import org.example.demo.infra.persistence.VideoJpaRepository
import org.example.demo.infra.persistence.VideoReactionJpaRepository
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository
import org.example.demo.domain.usecases.GetNextVideoUseCase
import org.example.demo.domain.usecases.GetTrendingVideosUseCase
import org.example.demo.domain.usecases.GetVideoByIdUseCase
import org.example.demo.domain.usecases.ReactToVideoUseCase
import org.example.demo.infra.time.SystemTimeProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DemoConfig {

    @Bean
    fun videoRepository(jpaRepository: VideoJpaRepository): VideoRepository = JpaVideoRepository(jpaRepository)

    @Bean
    fun videoReactionRepository(jpaRepository: VideoReactionJpaRepository): VideoReactionRepository = JpaVideoReactionRepository(jpaRepository)

    @Bean
    fun getNextVideoUseCase(repository: VideoRepository): GetNextVideoUseCase = GetNextVideoUseCase(repository)

    @Bean
    fun getVideoByIdUseCase(repository: VideoRepository): GetVideoByIdUseCase = GetVideoByIdUseCase(repository)

    @Bean
    fun getTrendingVideosUseCase(repository: VideoRepository): GetTrendingVideosUseCase = GetTrendingVideosUseCase(repository)

    @Bean
    fun getTimeProvider(): TimeProvider = SystemTimeProvider()

    @Bean
    fun getCurrentUserInfo(): CurrentUserInfo = UnknownCurrentUserInfo()

    @Bean
    fun reactToToVideoUseCase(
        videoRepository: VideoRepository,
        reactionRepository: VideoReactionRepository,
        timeProvider: TimeProvider,
        currentUserInfo: CurrentUserInfo,
    ): ReactToVideoUseCase = ReactToVideoUseCase(
        videoRepository,
        reactionRepository,
        timeProvider,
        currentUserInfo,
    )

}