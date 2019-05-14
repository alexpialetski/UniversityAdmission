package by.epam.pialetskialiaksei.sql.builder.api;

import java.sql.ResultSet;

public abstract class SetBuilder<T>{
    public abstract T build(ResultSet rs);
    public T buildForeign(ResultSet rs){
        return null;
    }
}
