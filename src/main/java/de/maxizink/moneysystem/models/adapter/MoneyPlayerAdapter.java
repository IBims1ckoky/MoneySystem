package de.maxizink.moneysystem.models.adapter;

import de.maxizink.moneysystem.models.MoneyPlayer;
import de.maxizink.moneysystem.utils.MongoAdapter;
import org.bson.Document;

import java.util.UUID;

public class MoneyPlayerAdapter implements MongoAdapter<MoneyPlayer> {

  public static final String KEY_UNIQUE_ID = "uniqueId";
  public static final String KEY_COINS = "coins";

  @Override
  public Document toDocument(final MoneyPlayer moneyPlayer) {
    return new Document()
        .append(KEY_UNIQUE_ID, moneyPlayer.getUniqueId())
        .append(KEY_COINS, moneyPlayer.getCoinsAmount());
  }

  @Override
  public MoneyPlayer fromDocument(final Document document) {
    return new MoneyPlayer(
        document.get(KEY_UNIQUE_ID, UUID.class),
        document.getLong(KEY_COINS)
    );
  }
}
