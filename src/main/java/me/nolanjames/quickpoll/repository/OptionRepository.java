package me.nolanjames.quickpoll.repository;

import me.nolanjames.quickpoll.domain.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {

}
