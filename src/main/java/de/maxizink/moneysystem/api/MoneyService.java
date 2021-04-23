package de.maxizink.moneysystem.api;

import de.maxizink.paperserviceregistry.serviceregistry.Service;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.UUID;

public interface MoneyService extends Service {

  Maybe<Long> getCoins(final UUID uuid);

  Single<Boolean> addCoins(final UUID uuid, final long coinsAmount);

  Single<Boolean> removeCoins(final UUID uuid, final long coinsAmount);

  Single<Boolean> hasEnoughCoins(final UUID uuid, final long coinsAmount);

  Single<Boolean> initiatePlayer(final UUID uuid);

  Completable resetPlayer(final UUID uuid);

}
