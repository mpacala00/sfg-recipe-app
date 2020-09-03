package pl.mpacala.sfgrecipieapp.services;

import pl.mpacala.sfgrecipieapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAll();
}
