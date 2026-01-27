package org.example.demo.api

import org.example.demo.domain.mocks.UnknownCurrentUserInfo
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.DomainEventPublisher
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.domain.ports.VideoPlatformApiFactory
import org.example.demo.infra.persistence.JpaVideoReactionRepository
import org.example.demo.infra.persistence.JpaVideoRepository
import org.example.demo.infra.persistence.VideoJpaRepository
import org.example.demo.infra.persistence.VideoReactionJpaRepository
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository
import org.example.demo.domain.usecases.GetNextVideoUseCase
import org.example.demo.domain.usecases.GetTrendingVideosUseCase
import org.example.demo.domain.usecases.GetVideoByRefUseCase
import org.example.demo.domain.usecases.ReactToVideoUseCase
import org.example.demo.infra.apis.FakeVideoPlatformApiFactory
import org.example.demo.infra.event.LogDomainEventPublisher
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
    fun getVideoByRefUseCase(repository: VideoRepository): GetVideoByRefUseCase = GetVideoByRefUseCase(repository)

    @Bean
    fun getTrendingVideosUseCase(repository: VideoRepository): GetTrendingVideosUseCase = GetTrendingVideosUseCase(repository)

    @Bean
    fun getTimeProvider(): TimeProvider = SystemTimeProvider()

    @Bean
    fun getCurrentUserInfo(): CurrentUserInfo = UnknownCurrentUserInfo()

    @Bean
    fun getDomainEventPublisher(): DomainEventPublisher = LogDomainEventPublisher()

    @Bean
    fun getVideoPlatformApiFactory(): VideoPlatformApiFactory = FakeVideoPlatformApiFactory()

    @Bean
    fun reactToVideoUseCase(
        videoRepository: VideoRepository,
        reactionRepository: VideoReactionRepository,
        timeProvider: TimeProvider,
        currentUserInfo: CurrentUserInfo,
        domainEventPublisher: DomainEventPublisher,
        videoPlatformApiFactory: VideoPlatformApiFactory,
    ): ReactToVideoUseCase = ReactToVideoUseCase(
        videoRepository,
        reactionRepository,
        timeProvider,
        currentUserInfo,
        domainEventPublisher,
        videoPlatformApiFactory,
    )

}