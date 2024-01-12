// Generated by Dagger (https://dagger.dev).
package com.gayathri.videplayercompose;

import androidx.lifecycle.SavedStateHandle;
import androidx.media3.common.Player;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<Player> playerProvider;

  private final Provider<MetaDataReader> metaDataReaderProvider;

  public MainViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<Player> playerProvider, Provider<MetaDataReader> metaDataReaderProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.playerProvider = playerProvider;
    this.metaDataReaderProvider = metaDataReaderProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(savedStateHandleProvider.get(), playerProvider.get(), metaDataReaderProvider.get());
  }

  public static MainViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<Player> playerProvider, Provider<MetaDataReader> metaDataReaderProvider) {
    return new MainViewModel_Factory(savedStateHandleProvider, playerProvider, metaDataReaderProvider);
  }

  public static MainViewModel newInstance(SavedStateHandle savedStateHandle, Player player,
      MetaDataReader metaDataReader) {
    return new MainViewModel(savedStateHandle, player, metaDataReader);
  }
}
