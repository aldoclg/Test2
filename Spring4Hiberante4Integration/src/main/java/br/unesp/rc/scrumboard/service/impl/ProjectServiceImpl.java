/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.rc.scrumboard.service.impl;

import br.unesp.rc.scrumboard.beans.Project;
import br.unesp.rc.scrumboard.dao.DAO;
import br.unesp.rc.scrumboard.dao.ProjectDAO;
import br.unesp.rc.scrumboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {
    
    @Autowired
    private ProjectDAO projectDao;
    
    @Override
    public DAO<Project> getDAO() {
        return this.projectDao;
    }
}
