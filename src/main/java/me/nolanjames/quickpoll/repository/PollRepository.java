package me.nolanjames.quickpoll.repository;

import me.nolanjames.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
