package de.maxizink.moneysystem.command;

import de.maxizink.moneysystem.api.MoneyService;
import de.maxizink.paperserviceregistry.serviceregistry.ServiceAccessor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoinsCommand implements CommandExecutor {

  private final MoneyService moneyService;

  public CoinsCommand(final ServiceAccessor serviceAccessor) {
    moneyService = serviceAccessor.accessService(MoneyService.class);
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                           @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    final Player player = (Player) sender;
    if (args.length == 0) {
      moneyService.getCoins(player.getUniqueId()).subscribe(coins ->
          player.sendMessage("§6Du hast derzeit §e" + coins + "§6 Coins"));
      return false;
    }
    if (args.length == 1) {
      final Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        player.sendMessage("§6Der Spieler ist offline!");
        return false;
      }
      moneyService.getCoins(target.getUniqueId()).subscribe(coins -> {
        player.sendMessage("§e" + target.getName() + " §6hat derzeit §e" + coins + "§6 Coins");
      });
      return true;
    }
    if (args.length == 2) {
      if (args[0].equalsIgnoreCase("reset")) {
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
          player.sendMessage("§6Der Spieler ist offline!");
          return false;
        }
        moneyService.resetPlayer(target.getUniqueId()).subscribe(() ->
            player.sendMessage("§6Du hast die Coins von §e" + target.getName() + " §6zurückgesetzt!"));
      }
      return true;
    }
    if (args.length == 3) {
      if (args[0].equalsIgnoreCase("add")) {
        long coins = Long.parseLong(args[2]);
        final Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
          player.sendMessage("§6Der Spieler ist offline!");
          return false;
        }
        moneyService.addCoins(target.getUniqueId(), coins).subscribe(successfully -> {
          if (successfully) {
            player.sendMessage("§6Du hast dem Spieler §e" + target.getName() + " " + coins + "§6 hinzugefügt!");
            return;
          }
          player.sendMessage("§6Fehler beim hinzufügen der Coins!");
        });
        return true;
      }
      if (args[0].equalsIgnoreCase("remove")) {
        long coins = Long.parseLong(args[2]);
        final Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
          player.sendMessage("§6Der Spieler ist offline!");
          return false;
        }
        moneyService.removeCoins(target.getUniqueId(), coins).subscribe(successfully -> {
          if (successfully) {
            player.sendMessage("§6Du hast dem Spieler §e" + target.getName() + " " + coins + "§6 entfernt!");
            return;
          }
          player.sendMessage("§6Fehler beim entfernen der Coins!");
        });
        return true;
      }
    }
    return false;
  }
}