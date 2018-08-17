package com.neo4j.triggers;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.logging.Log;

public class OthersTriggerProcess implements Runnable {
	private static TransactionData td;
	private static GraphDatabaseService db;
	private Log log;

	public OthersTriggerProcess(TransactionData transactionData, GraphDatabaseService graphDatabaseService,
			LogService logs) {
		td = transactionData;
		db = graphDatabaseService;
		log = logs.getUserLog(OthersTriggerProcess.class);
	}

	@Override
	public void run() {
		log.info("others event:" + this.td);

	}

}
