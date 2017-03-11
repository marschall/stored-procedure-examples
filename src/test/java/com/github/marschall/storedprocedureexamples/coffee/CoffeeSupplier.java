package com.github.marschall.storedprocedureexamples.coffee;

public final class CoffeeSupplier {

    private final String name;
    private final String coffee;

    CoffeeSupplier(String name, String coffee) {
      this.name = name;
      this.coffee = coffee;
    }

    @Override
    public String toString() {
        return "CoffeeSupplier(" +"name='" + name + "', coffee='" + coffee + "\')";
    }

    public String getName() {
      return this.name;
    }

    public String getCoffee() {
      return this.coffee;
    }
}