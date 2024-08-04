package domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import validator.ValidAuthors;
import validator.ValidISBN;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "id", "auteurs", "locaties", "favorieten", "isbn" })
@ToString(exclude = { "id" })
@JsonPropertyOrder({ "id", "naam", "isbn", "aankoopprijs", "aantalSterren", "auteurs", "locaties" })
public class Boek implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("boek_id")
	private Long id;
	@NotEmpty(message = "{boek.naam.message}")
	@JsonProperty("naam")
	private String naam;
	@JsonProperty("isbn")
	@ValidISBN
	private String isbn;
	@Range(min = 1, max = 100, message = "{boek.aankoopprijs.message}")
	@JsonProperty("aankoopprijs")
	private double aankoopprijs;
	@JsonProperty("aantalSterren")
	private int aantalSterren;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "boek_auteur", joinColumns = { @JoinColumn(name = "boek_id") }, inverseJoinColumns = {
			@JoinColumn(name = "auteur_id") })
	@JsonProperty("auteurs")
	@ValidAuthors
	private Set<Auteur> auteurs = new HashSet<>();

	@OneToMany(mappedBy = "boek", cascade = CascadeType.ALL)
	@JsonProperty("locaties")
	private Set<Locatie> locaties = new HashSet<>();

	@OneToMany(mappedBy = "boek", cascade = CascadeType.ALL)
	private Set<Favoriet> favorieten = new HashSet<>();

	public Boek(String naam, String ISBN, double aankoopprijs) {
		this.naam = naam;
		this.isbn = ISBN;
		this.aankoopprijs = aankoopprijs;
	}

	public void addLocatie(Locatie locatie) {
		locaties.add(locatie);
	}

	public void addAuteurs(List<Auteur> auteurs) {
		this.auteurs.addAll(auteurs);
		auteurs.forEach(auteur -> auteur.addBoeken(Arrays.asList(this)));
	}

	public void addFavoriet(Favoriet favoriet) {
		favorieten.add(favoriet);
	}

	public void incrementFavoriteCount() {
		aantalSterren++;
	}

	public void decrementFavoriteCount() {
		aantalSterren--;
	}

}
