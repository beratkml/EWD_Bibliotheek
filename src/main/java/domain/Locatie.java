package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@JsonPropertyOrder({ "id", "plaatsNaam", "plaatscode1", "plaatscode2", "boek" })
public class Locatie implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String plaatsNaam;

	@Min(value = 50, message = "{locatie1.min.message}")
	@Max(value = 300, message = "{locatie1.max.message}")
	private int plaatscode1;

	@Min(value = 50, message = "{locatie2.min.message}")
	@Max(value = 300, message = "{locatie2.max.message}")
	private int plaatscode2;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Boek boek;

	public Locatie(int plaatscode1, int plaatscode2, String plaatsnaam) {
		this.plaatscode1 = plaatscode1;
		this.plaatscode2 = plaatscode2;
		this.plaatsNaam = plaatsnaam;
	}
}
