package br.unesp.rc.scrumboard.controller;

import br.unesp.rc.scrumboard.service.Service;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe base para um controller
 * @author Vinicius
 */
public abstract class BaseController<T> {

    public BaseController() {
        
    }
    
    public abstract Service<T> getService();
    public abstract String getTypeName();
    public abstract boolean hasId(T entity);
    
    private final String listName = getTypeName() + "List";
    private final String formName = getTypeName() + "Form";
    private final String objectName = getTypeName() + "Object";
    
    @RequestMapping("getAll")
    public ModelAndView getAll(){
        List<T> list = getService().getAll();
        return new ModelAndView(listName, listName, list);
    }
    
    @RequestMapping("create")
    public ModelAndView create (Model model, @ModelAttribute T entity){
        model.addAttribute(entity);
        return new ModelAndView(formName);
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam long id, @ModelAttribute T entity) {
        entity = getService().get(id);
        return new ModelAndView(formName, objectName, entity);
    }

    @RequestMapping("save")
    public ModelAndView save(@ModelAttribute T entity) {    	
        if(hasId(entity))
            getService().update(entity);
        else
            getService().create(entity);
        return new ModelAndView("redirect:getAll");
    }

    @RequestMapping("delete")
    public ModelAndView delete(@RequestParam long id) {
        getService().delete(id);
        return new ModelAndView("redirect:getAll");
    }
    
    @RequestMapping("searchUser")
    public ModelAndView search(@RequestParam("searchName") String searchName) {
        List<T> list = getService().getByName(searchName);
        return new ModelAndView(listName, listName, list);
    }
}
