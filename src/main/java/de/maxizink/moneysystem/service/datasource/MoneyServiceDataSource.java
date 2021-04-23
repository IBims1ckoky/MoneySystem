package de.maxizink.moneysystem.service.datasource;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import de.maxizink.moneysystem.models.MoneyPlayer;
import de.maxizink.moneysystem.models.adapter.MoneyPlayerAdapter;
import de.maxizink.moneysystem.utils.MongoAdapter;
import de.maxizink.moneysystem.utils.MongoDataBase;
import org.bson.Document;

import java.util.UUID;

import static de.maxizink.moneysystem.models.adapter.MoneyPlayerAdapter.KEY_COINS;
import static de.maxizink.moneysystem.models.adapter.MoneyPlayerAdapter.KEY_UNIQUE_ID;

public class MoneyServiceDataSource {

  private static final String MONGO_COLLECTION = "MoneyPlayers";

  private final MongoCollection<Document> mongoCollection;
  private final MongoAdapter<MoneyPlayer> moneyPlayerMongoAdapter = new MoneyPlayerAdapter();

  public MoneyServiceDataSource(final MongoDataBase mongoDataBase) {
    this.mongoCollection = mongoDataBase.getDataBase().getCollection(MONGO_COLLECTION);
  }

  public long getCoins(final UUID uniqueId) {
    MoneyPlayer moneyPlayer = moneyPlayerMongoAdapter
        .toObject(mongoCollection.find(Filters.eq(KEY_UNIQUE_ID, uniqueId)).first());
    return moneyPlayer.getCoinsAmount();
  }

  public boolean insertPlayer(final UUID uniqueId) {
    Document playerDocument = moneyPlayerMongoAdapter.toDocument(new MoneyPlayer(uniqueId, 1000));
    if (!playerExists(uniqueId)) {
      mongoCollection.insertOne(playerDocument);
      return true;
    }
    return false;
  }

  public boolean playerExists(final UUID uniqueId) {
    return mongoCollection.find(Filters.eq(KEY_UNIQUE_ID, uniqueId)).first() != null;
  }

  public boolean addCoins(final UUID uniqueId, final long coinsAmount) {
    if (!playerExists(uniqueId)) {
      return false;
    }
    mongoCollection.updateOne(Filters.eq(KEY_UNIQUE_ID, uniqueId), Updates.inc(KEY_COINS, coinsAmount));
    return true;
  }

  public boolean removeCoins(final UUID uniqueId, final long coinsAmount) {
    if (!playerExists(uniqueId) && !hasEnoughCoins(uniqueId, coinsAmount)) {
      return false;
    }
    mongoCollection.updateOne(Filters.eq(KEY_UNIQUE_ID, uniqueId), Updates.inc(KEY_COINS, -coinsAmount));
    return true;
  }

  public boolean hasEnoughCoins(final UUID uniqueId, final long coinsAmount) {
    if (!playerExists(uniqueId)) {
      return false;
    }
    return moneyPlayerMongoAdapter
        .toObject(mongoCollection.find(Filters.eq(KEY_UNIQUE_ID, uniqueId)).first()).getCoinsAmount() > coinsAmount;
  }

  public void resetPlayer(final UUID uniqueId) {
    mongoCollection.updateOne(Filters.eq(KEY_UNIQUE_ID, uniqueId), Updates.inc(KEY_COINS, -getCoins(uniqueId)));
  }

}