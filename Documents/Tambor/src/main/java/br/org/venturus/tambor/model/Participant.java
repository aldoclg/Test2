package br.org.venturus.tambor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.org.venturus.tambor.utils.model.Profile;

@Entity
@Table(name="PARTICIPANTS")
@PrimaryKeyJoinColumn(name="ericsson_id")
public class Participant extends User implements Serializable {
	
	private static final long serialVersionUID = -5228610272605871856L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	private Area area = new Area();
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Profile profile;

	public Participant() {}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}	
	
	

}
