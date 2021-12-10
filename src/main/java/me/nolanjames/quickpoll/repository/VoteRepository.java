package me.nolanjames.quickpoll.repository;

import me.nolanjames.quickpoll.domain.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    @Query(value = "SELECT v.* from Option o, Vote v WHERE o.POLL_ID = ?1 and v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
    public Iterable<Vote> findByPoll(Long pollId);
}
