package com.sksamuel.camelwatch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sksamuel.camelwatch.CamelConnection;
import com.sksamuel.camelwatch.CamelConnectionFactory;
import com.sksamuel.camelwatch.consumer.Consumer;
import com.sksamuel.camelwatch.consumer.ConsumerOperations;

/**
 * @author Stephen K Samuel samspade79@gmail.com 1 Jul 2012 19:02:59
 * 
 */
@Controller
@RequestMapping("consumer/{consumerName}")
public class ConsumerController {

	@Autowired
	private CamelConnectionFactory	connectionFactory;

	String getRedirect(String consumerName) {
		return "redirect:/";
	}

	@RequestMapping
	public String show(@PathVariable("consumerName") String consumerName,
			ModelMap map,
			@RequestParam(value = "message", required = false) String message) throws Exception {
		CamelConnection conn = connectionFactory.getConnection();
		Consumer consumer = conn.getConsumer(consumerName);
		map.put("consumer", consumer);
		map.put("message", message);
		return "consumer";
	}

	@RequestMapping("start")
	public String start(@PathVariable("consumerName") String consumerName, ModelMap map) throws Exception {
		CamelConnection conn = connectionFactory.getConnection();
		ConsumerOperations ops = conn.getConsumerOperations(consumerName);
		ops.start();
		map.put("message", "Context '" + consumerName + "' started");
		return getRedirect(consumerName);
	}

	@RequestMapping("stop")
	public String stop(@PathVariable("consumerName") String consumerName, ModelMap map) throws Exception {
		CamelConnection conn = connectionFactory.getConnection();
		ConsumerOperations ops = conn.getConsumerOperations(consumerName);
		ops.stop();
		map.put("message", "Context '" + consumerName + "' stopped");
		return getRedirect(consumerName);
	}
}
