package com.yueny.demo.zookeeper.watcher;


public class SpeakOneMain {

	// public static void main(final String[] args) {
	// final String lockNode = "/zk_test/lock";
	// final SpeakOneByOneInCluster cluster = new SpeakOneByOneInCluster(
	// "localhost:20801", lockNode, "/zk_test/chooseCopyMachine");
	//
	// new Thread(new Speak(cluster, "/zk_test/chooseCopyMachine")).start();
	//
	// Runtime.getRuntime().addShutdownHook(new Thread() {
	// @Override
	// public void run() {
	// try {
	// if (cluster.zk.exists(lockNode, false) != null) {
	// cluster.zk.delete(lockNode, -1);
	// }
	// cluster.zk.close();
	// } catch (final Exception e) {
	// // logger.error(e.getMessage(), e);
	// }
	// }
	// });
	// }

}
