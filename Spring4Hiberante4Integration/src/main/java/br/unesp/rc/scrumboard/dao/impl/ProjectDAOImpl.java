package br.unesp.rc.scrumboard.dao.impl;

import br.unesp.rc.scrumboard.beans.Project;
import br.unesp.rc.scrumboard.dao.ProjectDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProjectDAOImpl extends BaseDAO<Project> implements ProjectDAO {

    @Override
    public Class<Project> getType() {
        return Project.class;
    }

    @Override
    public String getTableName() {
        return "Project";
    }

    @Override
    public List<String> getTableFields() {
        List<String> fields = new ArrayList();
        fields.add("id");
        fields.add("name");
        fields.add("description");
        fields.add("startDate");
        fields.add("endDate");
        return fields;
    }

    @Override
    public Project populateObject(Object[] object) {
        long id = (long) object[0];
        String name = (String) object[1];
        String description = (String) object[2];
        Date startDate = (Date) object[3];
        Date endDate = (Date) object[4];
        return new Project(id, name, description, startDate, endDate);
    }

    @Override
    public String getSearchNameField() {
        return "name";
    }

}
