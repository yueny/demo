// package com.yueny.demo.micros.cloud.observable;
//
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
//
// import org.reactivestreams.Subscriber;
//
// import io.reactivex.BackpressureStrategy;
// import io.reactivex.Flowable;
// import io.reactivex.FlowableEmitter;
// import io.reactivex.FlowableOnSubscribe;
//
/// **
// * 消费者工厂
// *
// * @author yueny09 <deep_blue_yang@163.com>
// *
// * @DATE 2017年5月22日 下午4:05:56
// *
// */
// public class SubscriberFactory {
// private static final Map<String, Subscriber<?>> annotationMap = new
// ConcurrentHashMap<>();
//
// /**
// * 取 Subscriber<T>
// *
// * @param key
// * 键
// */
// public static <T> Subscriber<T> get(final String key) {
// if (annotationMap.containsKey(key)) {
// return (Subscriber<T>) annotationMap.get(key);
// }
//
// return null;
// }
//
// /**
// * 放入 SlaRateLimit
// *
// * @param key
// * 键
// * @param annotation
// * SlaRateLimit
// */
// public static <T> Flowable<T> put(final String key, final List<T> ts) {
// if (annotationMap.containsKey(key)) {
// return false;
// }
//
// final Flowable<T> upstream = Flowable.create(new FlowableOnSubscribe<T>() {
// @Override
// public void subscribe(final FlowableEmitter<T> emitter) throws Exception {
// System.out.println("下游可以受理的请求量:" + emitter.requested());
//
// for (final T t : ts) {
// emitter.onNext(t);
// }
//
// emitter.onComplete();
// }
// }, BackpressureStrategy.BUFFER);
//
// annotationMap.put(key,
// RateLimitContextEntry.builder().slaRateLimit(annotation).createIfNull(createIfNull)
// .enableLimit(enableLimit).build());
// return true;
// }
//
// }
