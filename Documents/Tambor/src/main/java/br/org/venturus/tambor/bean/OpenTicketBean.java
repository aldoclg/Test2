package br.org.venturus.tambor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.primefaces.model.UploadedFile;

import br.org.venturus.tambor.dao.impl.StatusDaoImpl;
import br.org.venturus.tambor.dao.impl.TicketDaoImpl;
import br.org.venturus.tambor.model.Area;
import br.org.venturus.tambor.model.Status;
import br.org.venturus.tambor.model.Ticket;
import br.org.venturus.tambor.model.User;
import br.org.venturus.tambor.service.ActionService;
import br.org.venturus.tambor.service.AreaService;
import br.org.venturus.tambor.service.ParticipantService;
import br.org.venturus.tambor.service.StatusService;
import br.org.venturus.tambor.utils.jpa.JPAUtils;
import br.org.venturus.tambor.utils.model.Category;
import br.org.venturus.tambor.utils.model.EventType;
import br.org.venturus.tambor.utils.model.Priority;
import br.org.venturus.tambor.utils.session.SessionUtils;

@ManagedBean(name = "openTicketBean")
@RequestScoped
public class OpenTicketBean implements Serializable {

	private static final long serialVersionUID = 5026440787963260426L;
	private final int STATUS_OPEN = 1;		
	private TicketDaoImpl ticketDao;
	private List<Area> listOfAreas;	
	private Priority priority;
	private UploadedFile uploadFile;
	
	
	
	Ticket ticket = new Ticket();
	
	public OpenTicketBean() {
		
	}
	
	@PostConstruct
	public void init() {
		listOfAreas = AreaService.getAreas();	
	}
	
	public List<Area> getListOfAreas() {
		return listOfAreas;
	}

	public void setListOfAreas(List<Area> listOfAreas) {
		this.listOfAreas = listOfAreas;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public UploadedFile getUploadFile() {
		return uploadFile;
	}
	
	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
		ticket.setAttachment(this.uploadFile.getContents());
	}	
	
	private void fillTicket() {
		ticket.setUser((User) SessionUtils.getAttribute("user"));				
		ticket.setParticipant(ParticipantService.getSpoc(ticket.getArea()));
		Status s = StatusService.getStatusByName("Open");
		System.out.print("\n Status: " + s.getStatus() + " id: " + s.getId());
		ticket.setStatus(s);
		ticket.addAction(ActionService.generateAction(EventType.CREATE, ticket.getUser(), "Created by "));
	}
	
	public String create() {	
		
		ticketDao = new TicketDaoImpl(JPAUtils.getEntityManager());		
		
		fillTicket();
		
		ticketDao.insert(ticket);
		
		return "/core/myTickets.xhtml?faces-redirect=true";
	}
	
	@SuppressWarnings("static-access")
	public Priority[] getPriority() {
		
		return priority.values();	
	}

	
	public Category[] getCategory() {
		return Category.values();
	}

}
