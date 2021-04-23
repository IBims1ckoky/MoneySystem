package de.maxizink.moneysystem.utils;

import org.bson.Document;

public interface MongoAdapter<T> {

  Document toDocument(final T t);

  T toObject(final Document document);

}
