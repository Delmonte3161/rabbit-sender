package com.cerner.cts.oss.rabbit.sender;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author ds3161
 *
 */
@Data
public class IngestionEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2550496966080741261L;

	@JsonIgnore
	private String ingestionId;
	private String sourceId;
	private String tenantId;
	private String foreignId;
	private String targetDomain;
	private Map<String, Object> relationships;
	private Map<String, Object> payload;
	private String domainLink;
	private Integer retryCount = 0;
	private String status = "Pending";
	private String statusReason;
	private Integer timeToLive;
	private Boolean updateOnly = false;
}
