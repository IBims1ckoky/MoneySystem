package de.maxizink.moneysystem.utils;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.Callable;

public class ReactiveX {

  public static Completable completable(Action action) {
    return Completable.fromAction(action).subscribeOn(Schedulers.io());
  }

  public static <T> Single<T> single(Callable<T> callable) {
    return Single.fromCallable(callable).subscribeOn(Schedulers.io());
  }

  public static <T> Maybe<T> maybe(Callable<T> callable) {
    return Maybe.fromCallable(callable).subscribeOn(Schedulers.io());
  }

}