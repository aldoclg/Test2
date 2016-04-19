package br.org.venturus.tambor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.org.venturus.tambor.utils.model.Category;
import br.org.venturus.tambor.utils.model.Priority;
import br.org.venturus.tambor.model.Action;

@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 6484197219563212643L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	@ManyToOne
	@JoinColumn(name="ericsson_id_participants", nullable=false)
	private Participant participant = new Participant();
	
	@ManyToOne
	@JoinColumn(name="ericsson_id_users", nullable=false)
	private User user = new User();	

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Area area = new Area();
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Priority priority;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Column(nullable=false, length=1025)
	private String description;
	
	@Column(name="created_date", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createdDate = new Date();
	
	@Column(name="release_date", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date releaseDate = new Date();
	
	@Column(nullable=true)
	@Type(type="binary")
	private byte[] attachment;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Status status;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval=true, mappedBy="ticket")
	private Set<Action> actions = new TreeSet<Action>();
	
	public Ticket() {}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
	
	public void addAction(Action action) {	
		action.setTicket(this);
		actions.add(action);
	}
	
	@PrePersist
    protected void onCreate() {
		createdDate = new Date();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((participant == null) ? 0 : participant.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (id != other.id)
			return false;
		if (participant == null) {
			if (other.participant != null)
				return false;
		} else if (!participant.equals(other.participant))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}	
	
	
}
