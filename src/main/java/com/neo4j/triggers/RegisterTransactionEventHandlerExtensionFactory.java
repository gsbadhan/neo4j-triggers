package com.neo4j.triggers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.extension.KernelExtensionFactory;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.kernel.impl.spi.KernelContext;
import org.neo4j.kernel.lifecycle.Lifecycle;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;
import org.neo4j.logging.Log;

public class RegisterTransactionEventHandlerExtensionFactory
		extends KernelExtensionFactory<RegisterTransactionEventHandlerExtensionFactory.Dependencies> {
	private Log logger;

	interface Dependencies {
		GraphDatabaseService getGraphDatabaseService();
		LogService log();
	}

	public RegisterTransactionEventHandlerExtensionFactory() {
		super("registerTransactionEventHandler");
		this.logger = null;
	}

	@Override
	public Lifecycle newInstance(KernelContext context, Dependencies dependencies) {
		return new LifecycleAdapter() {
			 LogService log = dependencies.log();
	            private CustomTransactionHandler handler;
	            private ExecutorService executor;
	            
	            @Override
	            public void start() {
	                System.out.println("STARTING trigger watcher");
	                executor = Executors.newFixedThreadPool(2);
	                handler = new CustomTransactionHandler(dependencies.getGraphDatabaseService(), executor, log);
	                dependencies.getGraphDatabaseService().registerTransactionEventHandler(handler);
	            }

	            @Override
	            public void shutdown() {
	                System.out.println("STOPPING trigger watcher");
	                executor.shutdown();
	                dependencies.getGraphDatabaseService().unregisterTransactionEventHandler(handler);
	            }
		};
	}

	

}
