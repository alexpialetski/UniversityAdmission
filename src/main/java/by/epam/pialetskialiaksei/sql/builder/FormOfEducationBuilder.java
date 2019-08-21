package by.epam.pialetskialiaksei.sql.builder;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.FormOfEducation;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormOfEducationBuilder extends SetBuilder<FormOfEducation> {
    private final static Logger LOG = LogManager
            .getLogger(FormOfEducationBuilder.class);

    public FormOfEducation build(ResultSet rs) {
        FormOfEducation formOfEducation = new FormOfEducation();
        try {
            formOfEducation.setId(rs.getInt(Fields.ENTITY_ID));
            formOfEducation.setFormEng(rs.getString(Fields.FORM_OF_EDUCATION_FORM_ENG));
            formOfEducation.setFormRu(rs.getString(Fields.FORM_OF_EDUCATION_FORM_RU));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to report sheet", e);
        }
        return formOfEducation;
    }

    public FormOfEducation buildForeign(ResultSet rs) {
        FormOfEducation formOfEducation = new FormOfEducation();
        try {
            formOfEducation.setId(rs.getInt(Fields.FORM_OF_EDUCATION_ID_FOREIGN));
            formOfEducation.setFormEng(rs.getString(Fields.FORM_OF_EDUCATION_FORM_ENG));
            formOfEducation.setFormRu(rs.getString(Fields.FORM_OF_EDUCATION_FORM_RU));
        } catch (SQLException e) {
            LOG.error("Can not unmarshal ResultSet to report sheet", e);
        }
        return formOfEducation;
    }
}
