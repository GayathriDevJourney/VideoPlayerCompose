// Generated by Dagger (https://dagger.dev).
package com.gayathri.videplayercompose;

import android.app.Application;
import androidx.media3.common.Player;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("dagger.hilt.android.scopes.ViewModelScoped")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class VideoPlayerModule_ProvideVideoPlayerFactory implements Factory<Player> {
  private final Provider<Application> appProvider;

  public VideoPlayerModule_ProvideVideoPlayerFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public Player get() {
    return provideVideoPlayer(appProvider.get());
  }

  public static VideoPlayerModule_ProvideVideoPlayerFactory create(
      Provider<Application> appProvider) {
    return new VideoPlayerModule_ProvideVideoPlayerFactory(appProvider);
  }

  public static Player provideVideoPlayer(Application app) {
    return Preconditions.checkNotNullFromProvides(VideoPlayerModule.INSTANCE.provideVideoPlayer(app));
  }
}
