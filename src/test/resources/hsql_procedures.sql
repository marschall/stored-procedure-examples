DROP procedure IF EXISTS plus1inout
/;

CREATE procedure plus1inout (IN arg int, OUT res int)
BEGIN ATOMIC
  SET res = arg + 1;
END
/;

