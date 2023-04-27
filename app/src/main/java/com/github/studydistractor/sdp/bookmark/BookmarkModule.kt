package com.github.studydistractor.sdp.bookmark

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object BookmarksModule {
    @Provides
    fun provideBookmarkInterface() : Bookmarks {
        return FirebaseBookmarks()
    }
}