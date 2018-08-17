package com.neo4j.triggers;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.logging.Log;

public class CreateTriggerProcess implements Runnable {
	private static TransactionData td;
	private static GraphDatabaseService db;
	private Log log;

	public CreateTriggerProcess(TransactionData transactionData, GraphDatabaseService graphDatabaseService,
			LogService logs) {
		td = transactionData;
		db = graphDatabaseService;
		log = logs.getUserLog(CreateTriggerProcess.class);
	}

	@Override
	public void run() {
		try (Transaction tx = db.beginTx()) {
			for (Node node : td.createdNodes()) {
				if (node.hasLabel(Labels.user) || node.hasLabel(Labels.test)) {
					log.info("node created event:" + node);
				}
			}

		}
	}

}
