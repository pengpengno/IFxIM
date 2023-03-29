/*
 * Copyright (c) 2023 VMware, Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ifx.connect.reactor.netty.tcp;

import com.ifx.connect.handler.client.ClientInboundHandler;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpSslContextSpec;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TelnetClient {

	static final boolean SECURE = System.getProperty("secure") != null;
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", SECURE ? "8094" : "8094"));
	static final boolean WIRETAP = System.getProperty("wiretap") != null;
	static String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzgyNzM1ODcsImV4cCI6MTY3ODM1OTk4NywiQUNDT1VOVCI6eyJ1c2VySWQiOm51bGwsImFjY291bnQiOiJ3YW5ncGVuZyIsInVzZXJOYW1lIjpudWxsLCJlbWFpbCI6bnVsbH19.KjmiH4PXvzKmMOFMtpwWQjHdm8bpr8-c4_-oHxzH1vA";

	public static void main(String... args) {
		TcpClient client =
				TcpClient.create()
				         .host(HOST)
				         .port(PORT)
						.doOnChannelInit((connectionObserver, channel, remoteAddress) -> channel.pipeline().addFirst(new ClientInboundHandler(jwt)))
				         .doOnConnected(connection ->
				             connection.addHandlerLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())))
				         .wiretap("WIRETAP", LogLevel.INFO);

		if (SECURE) {
			TcpSslContextSpec tcpSslContextSpec =
					TcpSslContextSpec.forClient()
					                 .configure(builder -> builder.trustManager(InsecureTrustManagerFactory.INSTANCE));
			client = client.secure(spec -> spec.sslContext(tcpSslContextSpec));
		}

		Connection conn = client.connectNow();

		conn.inbound()
		    .receive()
		    .asString(StandardCharsets.UTF_8)
		    .doOnNext(System.out::println)
		    .subscribe();

		Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
		while (scanner.hasNext()) {
			String text = scanner.nextLine();
			conn.outbound()
			    .sendString(Mono.just(text + "\r\n"))
			    .then()
			    .subscribe();
			if ("bye".equalsIgnoreCase(text)) {
				break;
			}
		}

		conn.onDispose()
		    .block();
	}
}
