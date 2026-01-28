package org.example.demo.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.example.demo.domain.dtos.VideoReactionDto
import org.example.demo.domain.exceptions.InvalidReactionException
import org.example.demo.domain.exceptions.InvalidStateException
import org.example.demo.domain.exceptions.InvalidVideoRefException
import org.example.demo.domain.exceptions.VideoNotFoundException
import org.example.demo.domain.models.Video
import org.example.demo.domain.models.VideoId
import org.example.demo.domain.models.VideoRef
import org.example.demo.domain.ports.CurrentUserInfo
import org.example.demo.domain.ports.DomainEventPublisher
import org.example.demo.domain.ports.TimeProvider
import org.example.demo.domain.ports.VideoPlatformApi
import org.example.demo.domain.ports.VideoPlatformApiFactory
import org.example.demo.domain.ports.VideoReactionRepository
import org.example.demo.domain.ports.VideoRepository
import org.example.demo.domain.usecases.ReactToVideoUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

class ReactToVideoUseCaseTest {

    private val videoRepository = mockk<VideoRepository>()
    private val reactionRepository = mockk<VideoReactionRepository>(relaxed = true)
    private val timeProvider = mockk<TimeProvider>()
    private val currentUserInfo = mockk<CurrentUserInfo>()
    private val domainEventPublisher = mockk<DomainEventPublisher>(relaxed = true)
    private val videoPlatformApiFactory = mockk<VideoPlatformApiFactory>()
    private val platformApi = mockk<VideoPlatformApi>()

    private lateinit var useCase: ReactToVideoUseCase

    private val fixedNow = Instant.parse("2024-01-01T00:00:00Z")

    @BeforeEach
    fun setup() {
        useCase = ReactToVideoUseCase(
            videoRepository,
            reactionRepository,
            timeProvider,
            currentUserInfo,
            domainEventPublisher,
            videoPlatformApiFactory,
        )
    }

    @Test
    fun `happy path - reaction is created and event published`() = runTest {
        val videoRefString = "youtube:abc123"
        val videoRef = VideoRef.fromString(videoRefString)!!
        val video = Video(
            id = VideoId(1),
            platform = videoRef.platform,
            platformId = videoRef.platformId,
            title = "test"
        )

        val reactionDto = VideoReactionDto(
            reaction = "LIKE",
            timestamp = 10
        )

        coEvery { videoRepository.getVideoByRef(videoRef) } returns video
        every { videoPlatformApiFactory.create(video.platform) } returns platformApi
        coEvery { platformApi.fetchIsVideoExisting(video.platformId) } returns true
        coEvery { platformApi.fetchVideoDuration(video.platformId) } returns 120.seconds
        every { currentUserInfo.getUserName() } returns "john"
        every { timeProvider.now() } returns fixedNow

        useCase.execute(videoRefString, reactionDto)

        coVerify {
            reactionRepository.addReaction(
                match {
                    it.videoId == video.id &&
                            it.reaction == "LIKE" &&
                            it.userIdentity == "john" &&
                            it.timestamp == 10L &&
                            it.date == fixedNow
                }
            )
        }

        coVerify {
            domainEventPublisher.publish(match { it.startsWith("REACTION_CREATED") })
        }
    }

    @Test
    fun `invalid video ref throws InvalidVideoRefException`() = runTest {
        assertThrows<InvalidVideoRefException> {
            useCase.execute("bad-format", VideoReactionDto("LIKE", 0))
        }
    }

    @Test
    fun `video not found in repository throws VideoNotFoundException`() = runTest {
        val refString = "youtube:abc"
        val ref = VideoRef.fromString(refString)!!

        coEvery { videoRepository.getVideoByRef(ref) } returns null

        assertThrows<VideoNotFoundException> {
            useCase.execute(refString, VideoReactionDto("LIKE", 0))
        }
    }

    @Test
    fun `video not found on platform throws VideoNotFoundException`() = runTest {
        val refString = "youtube:abc"
        val ref = VideoRef.fromString(refString)!!
        val video = Video(VideoId(1), ref.platform, ref.platformId, "test")

        coEvery { videoRepository.getVideoByRef(ref) } returns video
        every { videoPlatformApiFactory.create(video.platform) } returns platformApi
        coEvery { platformApi.fetchIsVideoExisting(video.platformId) } returns false
        coEvery { platformApi.fetchVideoDuration(video.platformId) } returns 0.seconds

        assertThrows<VideoNotFoundException> {
            useCase.execute(refString, VideoReactionDto("LIKE", 10))
        }
    }

    @Test
    fun `invalid timestamp throws InvalidReactionException`() = runTest {
        val refString = "youtube:abc"
        val ref = VideoRef.fromString(refString)!!
        val video = Video(VideoId(1), ref.platform, ref.platformId, "test")

        coEvery { videoRepository.getVideoByRef(ref) } returns video
        every { videoPlatformApiFactory.create(video.platform) } returns platformApi
        coEvery { platformApi.fetchIsVideoExisting(video.platformId) } returns true
        coEvery { platformApi.fetchVideoDuration(video.platformId) } returns 30.seconds

        assertThrows<InvalidReactionException> {
            useCase.execute(refString, VideoReactionDto("LIKE", 100))
        }
    }

    @Test
    fun `platform api failure throws InvalidStateException`() = runTest {
        val refString = "youtube:abc"
        val ref = VideoRef.fromString(refString)!!
        val video = Video(VideoId(1), ref.platform, ref.platformId, "test")

        coEvery { videoRepository.getVideoByRef(ref) } returns video
        every { videoPlatformApiFactory.create(video.platform) } returns platformApi
        coEvery { platformApi.fetchIsVideoExisting(video.platformId) } throws RuntimeException("network down")

        assertThrows<InvalidStateException> {
            useCase.execute(refString, VideoReactionDto("LIKE", 10))
        }
    }
}