package de.maxizink.moneysystem.listener;

import de.maxizink.moneysystem.api.MoneyService;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

  private final MoneyService moneyService;

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    moneyService.initiatePlayer(player.getUniqueId()).subscribe();
  }

}
