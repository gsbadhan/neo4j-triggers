package com.neo4j.triggers;

import java.util.concurrent.ExecutorService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.kernel.impl.logging.LogService;

public class CustomTransactionHandler implements TransactionEventHandler{

	public static GraphDatabaseService db;
    private static ExecutorService ex;
    public static LogService logsvc;

    public CustomTransactionHandler(GraphDatabaseService graphDatabaseService, ExecutorService executor, LogService logsvc) {
        db = graphDatabaseService;
        ex = executor;
        this.logsvc = logsvc;
    }

    @Override
    public Object beforeCommit(TransactionData transactionData) throws Exception {
        return null;
    }

    @Override
    public void afterCommit(TransactionData transactionData, Object o) {
        ex.submit(new EventProcess(transactionData, db, logsvc));
    }

    @Override
    public void afterRollback(TransactionData transactionData, Object o) {

    }

}
