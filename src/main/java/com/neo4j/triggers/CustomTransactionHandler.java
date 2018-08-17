package com.neo4j.triggers;

import java.util.concurrent.ExecutorService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.kernel.impl.logging.LogService;

public class CustomTransactionHandler implements TransactionEventHandler{
	public static GraphDatabaseService db;
    private static ExecutorService ex;
    public static LogService logs;

    public CustomTransactionHandler(GraphDatabaseService graphDatabaseService, ExecutorService executor, LogService logs) {
        this.db = graphDatabaseService;
        this.ex = executor;
        this.logs = logs;
    }

    @Override
    public Object beforeCommit(TransactionData transactionData) throws Exception {
        return null;
    }

    @Override
    public void afterCommit(TransactionData transactionData, Object o) {
    	if(transactionData.createdNodes().iterator().hasNext()) {
    		ex.submit(new CreateTriggerProcess(transactionData, db, logs));	
    	}else if(transactionData.deletedNodes().iterator().hasNext()) {
    		ex.submit(new DeleteTriggerProcess(transactionData, db, logs));
    	}else {
    		ex.submit(new OthersTriggerProcess(transactionData, db, logs));
    	}
        
    }

    @Override
    public void afterRollback(TransactionData transactionData, Object o) {

    }

}
