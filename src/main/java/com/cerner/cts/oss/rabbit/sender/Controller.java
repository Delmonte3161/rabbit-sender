package com.cerner.cts.oss.rabbit.sender;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cerner.cts.oss.mass.device.contract.status.Status;
import com.cerner.cts.oss.mass.device.contract.status.StatusCode;
import com.cerner.cts.oss.mass.device.contract.status.StatusResource;

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
	@PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatusResource send(
			@RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
		StatusResource statusResource = new StatusResource();

		log.info("Sending messages!");
		service.send(count);

		Status status = new Status();
		status.setSuccess(true);
		status.setStatusCode(StatusCode.CREATED.getStatusCode());
		status.setStatusReason(
				"Publishing of " + count + " records in progress asynchronously...");

		statusResource.setStatus(status);
		return statusResource;
	}
}
