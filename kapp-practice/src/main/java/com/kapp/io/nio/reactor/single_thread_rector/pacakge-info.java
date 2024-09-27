/**
 * 在单线程的Reactor模型中，Reactor，Acceptor，Handler都只有一个线程来处理。
 *
 * <p>
 * Reactor组件负责监听所有连接事件。
 * 在服务端启动后，Reactor会将服务端处理客户端连接的管道--ServerSocketChannel，注册在多路复用器-Selector上，并将连接事件的具体处理对象-Acceptor注册上去。
 * 同时Acceptor会持有该ServerSocketChannel管道，以便获取到来的连接事件。
 * </p>
 * <p>
 * Acceptor：
 * 1.由于ServerSocketChannel管道被注册在Selector上，因此当有连接事件来临，Selector在ServerSocketChannel管道中进行通知。
 * 2.Acceptor内部可以通过ServerSocketChannel管道轻易获取到连接事件，并完成连接。
 * 3.同时Acceptor对于每一个连接完成后得到的客户端与服务端的通道--socketChannel，也会将其注册到Selector，由Selector负责事件的通知。
 * 4.而对于socketChannel中的发生的所有事件，则由具体的处理对象来负责
 * </p>
 * <p>
 * Handler:
 * 1.Handler是在客户端与服务端建立连接之后，处理后续所有事件的对象。
 * 2.Handler会持有客户端与服务端的连接管道--socketChannel，Selector会在通道有事件来临时进行及时通知，Handler则可以从通道中获取到具体事件并完成处理。
 * </p>
 *
 * @see com.kapp.io.nio.reactor.single_thread_rector.Reactor
 * @see com.kapp.io.nio.reactor.single_thread_rector.Acceptor
 * @see com.kapp.io.nio.reactor.single_thread_rector.Handler
 */
package com.kapp.io.nio.reactor.single_thread_rector;

