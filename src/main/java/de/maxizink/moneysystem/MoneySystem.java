package de.maxizink.moneysystem;

import de.maxizink.moneysystem.api.MoneyService;
import de.maxizink.moneysystem.command.CoinsCommand;
import de.maxizink.moneysystem.listener.PlayerJoinListener;
import de.maxizink.moneysystem.service.MaxiMoneyService;
import de.maxizink.moneysystem.service.datasource.MoneyServiceDataSource;
import de.maxizink.moneysystem.utils.MongoDataBase;
import de.maxizink.paperserviceregistry.serviceregistry.PaperPlugin;
import de.maxizink.paperserviceregistry.serviceregistry.ServiceAccessor;
import org.bukkit.Bukkit;

public class MoneySystem extends PaperPlugin {

  @Override
  public void onEnable() {
    ServiceAccessor serviceAccessor = getServiceAccessor();
    System.out.println("Enabling MoneySystem v1.0");

    MongoDataBase mongoDataBase = new MongoDataBase("host", 27017);
    mongoDataBase.connect("admin", "pw");

    MoneyServiceDataSource moneyServiceDataSource = new MoneyServiceDataSource(mongoDataBase);
    MoneyService moneyService = new MaxiMoneyService(moneyServiceDataSource);
    serviceAccessor.registerService(MoneyService.class, moneyService);

    getCommand("coins").setExecutor(new CoinsCommand(serviceAccessor));

    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(moneyService), this);
  }


}
