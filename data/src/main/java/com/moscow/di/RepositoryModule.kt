package com.moscow.di

import com.moscow.domain.repository.ActorRepository
import com.moscow.domain.repository.CategoryTipsRepository
import com.moscow.domain.repository.CollectionsRepository
import com.moscow.domain.repository.GenreRepository
import com.moscow.domain.repository.HistoryTipsRepository
import com.moscow.domain.repository.auth.LoginRepository
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.OnboardingRepository
import com.moscow.domain.repository.RatingTipsRepository
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.domain.repository.auth.ProfileRepository
import com.moscow.domain.repository.RecentlyViewedRepository
import com.moscow.domain.repository.SearchRepository
import com.moscow.domain.repository.SeriesRepository
import com.moscow.domain.repository.SessionRepository
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.service.theme.ThemeProvider
import com.moscow.preference.BlurProviderImpl
import com.moscow.preference.ThemeProviderImpl
import com.moscow.repository.ActorRepositoryImpl
import com.moscow.repository.CollectionsRepositoryImpl
import com.moscow.repository.GenreRepositoryImpl
import com.moscow.repository.MovieRepositoryImpl
import com.moscow.repository.ProfileRepositoryImpl
import com.moscow.repository.RecentlyViewedRepositoryImpl
import com.moscow.repository.SearchRepositoryImpl
import com.moscow.repository.SeriesRepositoryImpl
import com.moscow.repository.login.LoginRepositoryImpl
import com.moscow.repository.preference.CategoryTipsRepositoryImpl
import com.moscow.repository.preference.HistoryTipsRepositoryImpl
import com.moscow.repository.preference.OnboardingRepositoryImpl
import com.moscow.repository.preference.RatingTipsRepositoryImpl
import com.moscow.repository.preference.SessionRepositoryImpl
import com.moscow.repository.preference.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindSeriesRepository(impl: SeriesRepositoryImpl): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(impl: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    abstract fun bindActorRepository(impl: ActorRepositoryImpl): ActorRepository

    @Binds
    @Singleton
    abstract fun bindCollectionsRepository(impl: CollectionsRepositoryImpl): CollectionsRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository


    @Binds
    @Singleton
    abstract fun bindThemeProvider(impl: ThemeProviderImpl): ThemeProvider

    @Binds
    @Singleton
    abstract fun bindBlurProvider(impl: BlurProviderImpl): BlurProvider

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository


    @Binds
    @Singleton
    abstract fun bindCategoryTipsRepository(impl: CategoryTipsRepositoryImpl): CategoryTipsRepository

 @Binds
    @Singleton
    abstract fun bindRatingTipsRepository(impl: RatingTipsRepositoryImpl): RatingTipsRepository

 @Binds
    @Singleton
    abstract fun bindHistoryTipsRepository(impl: HistoryTipsRepositoryImpl): HistoryTipsRepository


    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository


    @Binds
    @Singleton
    abstract fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository


    @Binds
    @Singleton
    abstract fun bindRecentlyViewedRepository(impl: RecentlyViewedRepositoryImpl): RecentlyViewedRepository
}