package com.yueny.demo.exec.pair;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.yueny.demo.exec.pair.task.PairChecker;
import com.yueny.demo.exec.pair.task.PairManipulator;

public class CirticalSectionMain {
	public static void main(final String[] args) {
		final AbstractPairManager p1 = new DeafultPairManager();
		final AbstractPairManager p2 = new CirticalPairManager();

		testApproaches(p1, p2);
	}

	// test two different approaches;
	static void testApproaches(final AbstractPairManager pman1,
			final AbstractPairManager pman2) {
		System.out.println("test two different approaches...");

		final ExecutorService exec = Executors.newCachedThreadPool();

		final PairManipulator pm1 = new PairManipulator(pman1);
		final PairManipulator pm2 = new PairManipulator(pman2);

		final PairChecker pc1 = new PairChecker(pman1);
		final PairChecker pc2 = new PairChecker(pman2);

		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pc1);
		exec.execute(pc2);

		System.out.println("sleep 500 ...");
		try {
			TimeUnit.SECONDS.sleep(500);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}

		System.out.println("pm1:" + pm1 + ",pm2" + pm2);
		System.exit(0);
	}
}
