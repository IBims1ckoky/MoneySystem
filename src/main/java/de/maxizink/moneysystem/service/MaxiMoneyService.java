package de.maxizink.moneysystem.service;

import de.maxizink.moneysystem.api.MoneyService;
import de.maxizink.moneysystem.service.datasource.MoneyServiceDataSource;
import de.maxizink.moneysystem.utils.ReactiveX;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class MaxiMoneyService implements MoneyService {

  private final MoneyServiceDataSource moneyServiceDataSource;

  @Override
  public Maybe<Long> getCoins(final UUID uuid) {
    return ReactiveX.maybe(() -> moneyServiceDataSource.getCoins(uuid));
  }

  @Override
  public Single<Boolean> addCoins(final UUID uniqueId, final long coinsAmount) {
    return ReactiveX.single(() -> moneyServiceDataSource.addCoins(uniqueId, coinsAmount));
  }

  @Override
  public Single<Boolean> removeCoins(final UUID uniqueId, final long coinsAmount) {
    return ReactiveX.single(() -> moneyServiceDataSource.removeCoins(uniqueId, coinsAmount));
  }

  @Override
  public Single<Boolean> hasEnoughCoins(final UUID uniqueId, final long coinsAmount) {
    return ReactiveX.single(() -> moneyServiceDataSource.hasEnoughCoins(uniqueId, coinsAmount));
  }

  @Override
  public Single<Boolean> initiatePlayer(final UUID uniqueId) {
    return ReactiveX.single(() -> moneyServiceDataSource.insertPlayer(uniqueId));
  }

  @Override
  public Completable resetPlayer(final UUID uuid) {
    return ReactiveX.completable(() -> moneyServiceDataSource.resetPlayer(uuid));
  }
}