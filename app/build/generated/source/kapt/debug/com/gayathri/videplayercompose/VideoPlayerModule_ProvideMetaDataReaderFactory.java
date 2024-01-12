// Generated by Dagger (https://dagger.dev).
package com.gayathri.videplayercompose;

import android.app.Application;
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
public final class VideoPlayerModule_ProvideMetaDataReaderFactory implements Factory<MetaDataReader> {
  private final Provider<Application> appProvider;

  public VideoPlayerModule_ProvideMetaDataReaderFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public MetaDataReader get() {
    return provideMetaDataReader(appProvider.get());
  }

  public static VideoPlayerModule_ProvideMetaDataReaderFactory create(
      Provider<Application> appProvider) {
    return new VideoPlayerModule_ProvideMetaDataReaderFactory(appProvider);
  }

  public static MetaDataReader provideMetaDataReader(Application app) {
    return Preconditions.checkNotNullFromProvides(VideoPlayerModule.INSTANCE.provideMetaDataReader(app));
  }
}
