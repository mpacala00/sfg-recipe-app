package pl.mpacala.sfgrecipieapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne //no cascade because Recipe owns the relation
    private Recipe recipe;

    @Lob //If data is text and is not enough to save in VARCHAR, then that data should be saved in CLOB
    private String recipeNotes;
}
