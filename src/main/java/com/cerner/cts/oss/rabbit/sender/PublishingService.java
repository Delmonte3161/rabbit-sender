package com.cerner.cts.oss.rabbit.sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PublishingService {
	private final Publisher publisher;

	@Async
	public void send(Integer count) {

		Integer publishedCount = 0;
		List<IngestionEvent> events = getEvents(count);
		for (IngestionEvent ingestionEvent : events) {
			log.debug("Sending {}", ingestionEvent);
			if (!publisher.publish(ingestionEvent))
				log.warn("Could not send: {}", ingestionEvent);
			else
				publishedCount++;
		}
		log.info("Published {} messages.", publishedCount);
	}

	private List<IngestionEvent> getEvents(Integer count) {
		List<IngestionEvent> eventsOut = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			IngestionEvent event = new IngestionEvent();

			event.setSourceId("TestSource");
			event.setTenantId("TestTenant");
			event.setTargetDomain("TestTarget");
			event.setForeignId("ForeignId-" + i);

			String hostname = "test-device-" + i;
			String domain = "test-domain.com";
			String ip = "127.0.0." + (i % 50);

			// Set device identifiers up in relationships map
			Map<String, Object> relationships = new HashMap<>();
			relationships.put("name", hostname);
			relationships.put("domain:", domain);
			relationships.put("ip", ip);
			event.setRelationships(relationships);

			// Move all source fields to payload
			Map<String, Object> devicePayload = new HashMap<>();
			devicePayload.put("deviceType", "Network");
			devicePayload.put("deviceId", event.getForeignId());
			devicePayload.put("flashMemory", 4);
			devicePayload.put("freePorts", 10);
			devicePayload.put("geographicalLocation", "Bahamas");
			devicePayload.put("hostname", hostname);
			devicePayload.put("lastAccessAttemptDate", new Date());
			devicePayload.put("lastAccessSuccessDate", new Date());
			devicePayload.put("lastSnapshotAttemptStatus", 1);
			devicePayload.put("managementStatus", 1);
			devicePayload.put("memory", 4);
			devicePayload.put("model", "Kathy Ireland");
			devicePayload.put("primaryFQDN", hostname + "." + domain);
			devicePayload.put("primaryIpAddress", ip);
			devicePayload.put("processor", "Meat Packing");
			devicePayload.put("serialNumber", "Capt Crunch 95");
			devicePayload.put("softwareVersion", "Wearable Blanket");
			devicePayload.put("totalPorts", 12);
			devicePayload.put("vendor", "Hot Dog");

			event.setPayload(devicePayload);
			eventsOut.add(event);
		}
		return eventsOut;
	}
}
