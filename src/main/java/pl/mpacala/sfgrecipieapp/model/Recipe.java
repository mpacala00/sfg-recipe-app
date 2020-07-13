package pl.mpacala.sfgrecipieapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    //EnumType.ORDINAL is default, it will persists enum values as 1, 2, 3... to db
    //STRING will persists the values as strings, ORDINAL may break if we update Difficulty with new values
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    //Lob - large object (storage in database)
    @Lob //will save this field in BLOB type in db
    private Byte[] image;

    //cascade makes the Recipe the owner of Notes
    //if we deleted a Recipe from database its notes would be deleted as well
    //ALL means persisting all operations
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    //using @JoinTable to prevent database from creating 2 separate tables
    @JoinTable(name = "recipe_category", //name of the table in db
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this); //bidirectional relationship with only 1 method
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this); // bidir association
        this.ingredients.add(ingredient);
        return this;
    }
}
