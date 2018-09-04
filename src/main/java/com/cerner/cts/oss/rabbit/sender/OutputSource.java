package com.cerner.cts.oss.rabbit.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author ds3161
 *
 */
@Component
public interface OutputSource {
	/***/
	String myOutput = "myOutput";

	/**
	 * @return MessageChannel
	 */
	@Output(value = OutputSource.myOutput)
	MessageChannel output();

}