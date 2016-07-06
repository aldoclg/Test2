package br.unesp.rc.scrumboard.controller;

import br.unesp.rc.scrumboard.beans.Project;
import br.unesp.rc.scrumboard.service.ProjectService;
import br.unesp.rc.scrumboard.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para ações referentes a projeto
 * @author Vinicius
 */
@Controller
@RequestMapping("project")
public class ProjectController extends BaseController<Project>{
    
    @Autowired
    private ProjectService projectService;
    
    @Override
    public Service<Project> getService() {
        return this.projectService;
    }

    @Override
    public String getTypeName() {
        return Project.class.getSimpleName().toLowerCase();
    }

    @Override
    public boolean hasId(Project entity) {
        return entity.getId() > 0;
    }
    
}
