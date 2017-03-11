package com.github.marschall.storedprocedureexamples;

import static java.sql.Types.INTEGER;

import org.junit.Before;

import spwrap.Config;
import spwrap.DAO;
import spwrap.annotations.Param;
import spwrap.annotations.Scalar;
import spwrap.annotations.StoredProc;

public class SpWrapTest extends AbstractExampleTest {

  private MathDAO mathDao;

  @Before
  public void setUp() {
    DAO dao = new DAO.Builder(this.getDataSource())
            .config(new Config().useStatusFields(false))
            .build();
    this.mathDao = dao.create(MathDAO.class);
  }

  @Override
  protected int plus1inout(int arg) {
    return this.mathDao.plus1inout(arg);
  }

  interface MathDAO {

    @Scalar(INTEGER)
    @StoredProc("plus1inout")
    int plus1inout(@Param(INTEGER) int arg);
  }

}
