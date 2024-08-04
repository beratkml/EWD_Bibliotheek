package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "authorities")
@Table(name = "authorities", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "authority" }) })
@NoArgsConstructor
@Getter
@Setter
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;
	private String authority;
	private String username;

	@ManyToOne()
	@JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
	private User gebruiker;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
