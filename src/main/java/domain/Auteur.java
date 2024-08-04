package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "id", "boeken" })
@ToString(exclude = { "id", "boeken" })
@JsonPropertyOrder({ "id", "naam", "boeken" })
public class Auteur implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;
	@JsonProperty("naam")
	private String naam;

	@ManyToMany(mappedBy = "auteurs", fetch = FetchType.EAGER)
	@JsonProperty("boeken")
	@JsonIgnore
	private Set<Boek> boeken = new HashSet<>();

	public Auteur(String naam) {
		this.naam = naam;
	}

	public void addBoeken(List<Boek> boeken) {
		this.boeken.addAll(boeken);
	}

}
