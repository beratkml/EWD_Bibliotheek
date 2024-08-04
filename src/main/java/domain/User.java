package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "favorieten" })
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String password;

	private boolean enabled;

	private int maxAantalFavorieten;
	@OneToMany(mappedBy = "gebruiker", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Authority> auths = new HashSet<>();

	@OneToMany(mappedBy = "gebruiker", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Favoriet> favorieten = new HashSet<>();

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public void addFavorite(Boek book) {
		Favoriet favorite = new Favoriet();
		favorite.setGebruiker(this);
		favorite.setBoek(book);
		favorieten.add(favorite);
		book.incrementFavoriteCount();
	}

	public void removeFavorite(Boek book) {
		Favoriet favoriteToRemove = null;
		for (Favoriet favorite : favorieten) {
			if (favorite.getBoek().equals(book)) {
				favoriteToRemove = favorite;
				break;
			}
		}
		if (favoriteToRemove != null) {
			favorieten.remove(favoriteToRemove);
			book.decrementFavoriteCount();
		}
	}

}
