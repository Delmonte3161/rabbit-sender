package com.cerner.cts.oss.rabbit.sender;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Controller {
	private final PublishingService service;

	/**
	 * @param record
	 */
	@PostMapping(path = "/send", produces = MediaType.TEXT_PLAIN_VALUE)
	public String send(
			@RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
		log.info("Sending messages!");
		service.send(count);

		return "Publishing of " + count + " records in progress asynchronously...";
	}
}
