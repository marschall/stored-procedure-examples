package com.github.marschall.storedprocedureexamples;

import com.github.marschall.storedprocedureproxy.annotations.OutParameter;

public interface Procedures {

  @OutParameter
  int plus1inout(int arg);

}
