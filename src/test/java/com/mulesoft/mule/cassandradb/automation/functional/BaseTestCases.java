/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.mule.cassandradb.automation.functional;

import com.datastax.driver.core.DataType;
import com.mulesoft.mule.cassandradb.api.CassandraClient;
import com.mulesoft.mule.cassandradb.util.PropertiesLoaderUtil;
import com.mulesoft.mule.cassandradb.utils.CassandraConfig;
import com.mulesoft.mule.cassandradb.utils.Constants;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import com.mulesoft.mule.cassandradb.CassandraDBConnector;


public class BaseTestCases extends AbstractTestCase<CassandraDBConnector> {

    public BaseTestCases() {
        super(CassandraDBConnector.class);
    }

    public static CassandraClient configureClient(CassandraConfig cassConfig) throws Exception {

        //get instance of cass client based on the configs
        CassandraClient cassClient = CassandraClient.buildCassandraClient(cassConfig.getHost(), cassConfig.getPort(), null, null, null);
        assert cassClient != null;

        //setup db env
        cassClient.createTable(Constants.TABLE_NAME, cassConfig.getKeyspace(), null);
        cassClient.addColumnToTable(Constants.TABLE_NAME, cassConfig.getKeyspace(), Constants.VALID_COLUMN, DataType.text());

        return cassClient;
    }

    public static CassandraConfig getClientConfig() throws ConfigurationLoadingFailedException {
        //load required properties
        CassandraConfig cassConfig = PropertiesLoaderUtil.resolveCassandraConnectionProps();
        assert cassConfig != null;

        return cassConfig;
    }

}
