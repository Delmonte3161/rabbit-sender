package com.cerner.cts.oss.rabbit.sender;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.cerner.cts.oss.mass.ingest.contract.IngestionEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableBinding(OutputSource.class)
public class Publisher {
	private final OutputSource source;

	/**
	 * @param message
	 * @return boolean
	 */
	public Boolean publish(IngestionEvent payload) {
		MessageChannel channel = source.output();
		return channel.send(MessageBuilder.withPayload(payload).build());
	}
}
