package pl.mpacala.sfgrecipieapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * These command objects are used to transfer data to and from forms.
 * They are very similar to Entities, but they do not have any special methods
 * such as equals() and hashCode() nor any fields that are representing relations
 * to them in a database (annotated by @ManyToMany etc.)
 */

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
