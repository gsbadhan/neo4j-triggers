package com.neo4j.triggers;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.logging.Log;

public class DeleteTriggerProcess implements Runnable{
	private static TransactionData td;
    private static GraphDatabaseService db;
    private Log log;
    
    
    public DeleteTriggerProcess (TransactionData transactionData, GraphDatabaseService graphDatabaseService, LogService logs) {
        td = transactionData;
        db = graphDatabaseService;
        log = logs.getUserLog(DeleteTriggerProcess.class);
    }
    
	@Override
	public void run() {
		try (Transaction tx = db.beginTx()) {
			for (Node node : td.deletedNodes()) {
				if (node.hasLabel(Labels.user) || node.hasLabel(Labels.test)) {
					log.info("node deleted event:" + node);
				}
			}

		}
	}

}
