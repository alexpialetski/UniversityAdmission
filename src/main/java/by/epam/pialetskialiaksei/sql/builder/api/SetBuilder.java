package by.epam.pialetskialiaksei.sql.builder.api;

import java.sql.ResultSet;

public interface SetBuilder<T>{
    T build(ResultSet rs);
}
