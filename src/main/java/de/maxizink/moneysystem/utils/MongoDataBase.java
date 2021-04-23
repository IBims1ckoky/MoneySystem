package de.maxizink.moneysystem.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class MongoDataBase {

  private final String hostName;
  private final int port;

  private MongoClient mongoClient;
  private MongoDatabase dataBase;

  public void connect(final String userName, final String passWord) {
    MongoCredential credential = MongoCredential.createCredential(userName, "admin", passWord.toCharArray());
    mongoClient = new MongoClient(new ServerAddress(hostName, port), Collections.singletonList(credential));
    dataBase = mongoClient.getDatabase("admin");
  }

  public MongoDatabase getDataBase() {
    return dataBase;
  }
}
