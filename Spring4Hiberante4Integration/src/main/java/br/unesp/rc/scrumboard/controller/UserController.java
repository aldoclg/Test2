package br.unesp.rc.scrumboard.controller;

import br.unesp.rc.scrumboard.beans.User;
import br.unesp.rc.scrumboard.service.Service;
import br.unesp.rc.scrumboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador responsável pelas operações sobre a classe User
 *
 * @author Vinicius
 */
@Controller
public class UserController extends BaseController<User>{
    
    @Autowired
    private UserService userService;

    public UserController() {
        
    }

    @Override
    public Service<User> getService() {
        return this.userService;
    }

    @Override
    public String getTypeName() {
        return User.class.getSimpleName().toLowerCase();
    }
    
    @RequestMapping(value = {"loginForm", "/"})
    public ModelAndView loginForm(Model model){
        model.addAttribute("user", new User());
        return new ModelAndView("login");
    }
    
    @RequestMapping("login")
    public ModelAndView login(@ModelAttribute User user){
        boolean valid = userService.isValid(user);
        if (valid) {
            return new ModelAndView("redirect:project/getAll");
        }
        return new ModelAndView("redirect:/","invalid",!valid);
    }
    
    @RequestMapping("deleteUser")
    public ModelAndView delete(@RequestParam String email) {
        userService.delete(email);
        List<User> list = userService.getAll();
        return new ModelAndView("userList","userList",list);
    }

    @Override
    public boolean hasId(User entity) {
    	if (userService.get(entity.getEmail()) != null) return true;
        return false;
    }
}
