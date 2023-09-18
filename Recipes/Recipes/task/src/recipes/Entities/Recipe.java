package recipes.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user", referencedColumnName = "email")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "listOfIngredients")
    private List<String> ingredients;

    @ElementCollection
    @CollectionTable(name = "listOfDirections")
    private List<String> directions;

    @Column(name = "category")
    private String category;

    @Column(name = "date")
    private LocalDateTime date;

    public void setValues(User user, String name, String description, List<String> ingredients,
                          List<String> directions, String category, LocalDateTime date) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = date;
    }
}
