package com.github.marschall.storedprocedureexamples;

import java.math.BigDecimal;
import java.util.List;

import com.github.marschall.storedprocedureproxy.ValueExtractor;
import com.github.marschall.storedprocedureproxy.annotations.OutParameter;

public interface Coffee {

  List<CoffeeSupplier> showSuppliers(ValueExtractor<CoffeeSupplier> extractor);

  @OutParameter
  String getSupplierOfCoffee(String coffeeName);

  BigDecimal raisePrice(String coffeeName, float maximumPercentage);

  final class CoffeeSupplier {

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

}
