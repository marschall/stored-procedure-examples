package com.github.marschall.storedprocedureexamples.coffee;

import java.math.BigDecimal;
import java.util.List;

import com.github.marschall.storedprocedureproxy.ValueExtractor;
import com.github.marschall.storedprocedureproxy.annotations.InOutParameter;
import com.github.marschall.storedprocedureproxy.annotations.OutParameter;

/**
 * Interface for calling stored procedures.
 */
public interface Coffee {

  List<CoffeeSupplier> showSuppliers(ValueExtractor<CoffeeSupplier> extractor);

  @OutParameter
  String getSupplierOfCoffee(String coffeeName);

  @InOutParameter
  BigDecimal raisePrice(String coffeeName, float maximumPercentage, BigDecimal newPrice);

}
