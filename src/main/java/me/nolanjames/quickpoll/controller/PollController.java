package me.nolanjames.quickpoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.nolanjames.quickpoll.domain.Poll;
import me.nolanjames.quickpoll.dto.error.ErrorDetail;
import me.nolanjames.quickpoll.exception.ResourceNotFoundException;
import me.nolanjames.quickpoll.repository.PollRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@Api(value = "polls", description = "Poll API")
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @GetMapping("/polls")
    @ApiOperation(value = "Retrieves all the polls", response = Poll.class, responseContainer = "List")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @GetMapping("/polls/{pollId}")
    @ApiOperation(value = "Retrieves given Poll", response = Poll.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = Poll.class),
            @ApiResponse(code = 404, message = "Unable to find Poll", response = ErrorDetail.class)})
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
    }

    @PostMapping("/polls")
    @ApiOperation(value = "Creates a new Poll", notes = "The newly created poll Id will be sent in the location response header",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll Created Successfully", response = Void.class),
            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class)})
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/polls/{pollId}")
    @ApiOperation(value = "Updates given Poll", response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = Void.class),
            @ApiResponse(code = 404, message = "Unable to find Poll", response = ErrorDetail.class)})
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/polls/{pollId}")
    @ApiOperation(value = "Deletes given Poll", response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = Void.class),
            @ApiResponse(code = 404, message = "Unable to find Poll", response = ErrorDetail.class)})
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollRepository.deleteById(pollId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);

        if (!poll.isPresent()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }

        return poll.get();
    }


}
