/*
 * Copyright 2013-2015 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cassandra.test.integration.core.cql.generator;

import static org.springframework.cassandra.test.integration.core.cql.generator.CqlKeyspaceSpecificationAssertions.assertKeyspace;

import org.junit.Test;
import org.springframework.cassandra.test.integration.AbstractEmbeddedCassandraIntegrationTest;
import org.springframework.cassandra.test.unit.core.cql.generator.CreateKeyspaceCqlGeneratorTests.BasicTest;
import org.springframework.cassandra.test.unit.core.cql.generator.CreateKeyspaceCqlGeneratorTests.CreateKeyspaceTest;
import org.springframework.cassandra.test.unit.core.cql.generator.CreateKeyspaceCqlGeneratorTests.NetworkTopologyTest;

/**
 * Integration tests that reuse unit tests.
 * 
 * @author John McPeek
 * @author Oliver Gierke
 */
public class CreateKeyspaceCqlGeneratorIntegrationTests {

	/**
	 * Integration test base class that knows how to do everything except instantiate the concrete unit test type T.
	 * 
	 * @param <T> The concrete unit test class to which this integration test corresponds.
	 */
	public static abstract class Base<T extends CreateKeyspaceTest> extends AbstractEmbeddedCassandraIntegrationTest {
		T unit;

		public abstract T unit();

		@Test
		public void test() {
			unit = unit();
			unit.prepare();

			system.execute(unit.cql);

			assertKeyspace(unit.specification, unit.keyspace, system);
		}
	}

	public static class BasicIntegrationTest extends Base<BasicTest> {

		@Override
		public BasicTest unit() {
			return new BasicTest();
		}
	}

	public static class NetworkTopologyIntegrationTest extends Base<NetworkTopologyTest> {

		@Override
		public NetworkTopologyTest unit() {
			return new NetworkTopologyTest();
		}
	}
}
