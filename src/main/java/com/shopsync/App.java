package com.shopsync;

import com.shopsync.application.CartService;
import com.shopsync.application.StoreService;
import com.shopsync.cli.ShopSyncCLI;
import com.shopsync.domain.repository.StoreRepository;
import com.shopsync.infrastructure.persistence.inmemory.StoreInMemoryRepository;

public class App {
  public static void main(String[] args) {
    StoreRepository storeRepository = new StoreInMemoryRepository();
    StoreService storeService = new StoreService(storeRepository);
    CartService cartService = new CartService();

    ShopSyncCLI cli = new ShopSyncCLI(storeService, cartService);
    cli.start();
  }
}
