package de.maxizink.moneysystem.models;

import lombok.Data;

import java.util.UUID;

@Data
public class MoneyPlayer {

  private final UUID uniqueId;
  private final long coinsAmount;

}
