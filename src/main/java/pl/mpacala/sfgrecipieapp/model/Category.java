package pl.mpacala.sfgrecipieapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes") //resolves the stack overflow problem because of bidirectional realtions
@Entity
public class Category {
    //option + enter -> create test

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories") //needed to prevent 2 tables from being created
    private Set<Recipe> recipes;

}
