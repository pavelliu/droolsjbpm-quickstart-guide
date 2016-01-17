package com.wordpress.ezegrande.drools.examples.simple;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordpress.ezegrande.drools.examples.TestUtil;
import com.wordpress.ezegrande.drools.examples.model.PaymentMethod;
import com.wordpress.ezegrande.drools.examples.model.Purchase;

public class CPUMetricTestCase {
    private Logger logger = LoggerFactory.getLogger(CPUMetricTestCase.class);

    private static final String DRL_PATH = "com/wordpress/ezegrande/drools/examples/simple/CPUMetric.drl";

    @Test
    public void test() {
        logger.info("Starting @Test testStatelessSession()");
        // Create the Stateless Session
        StatelessKieSession session = TestUtil.createStatelessKieSession(DRL_PATH);
        // Add SLF4j Logger as a Global Variable
        session.setGlobal("logger", logger);
        // Create our 'input' objects, that will be inserted into the Session
        Purchase cashPurchase = new Purchase("john", 100, PaymentMethod.CASH);
        Purchase debitPurchase = new Purchase("peter", 100, PaymentMethod.DEBIT);
        Purchase creditPurchase = new Purchase("george", 100, PaymentMethod.CREDIT);

        logger.info("Executing Stateless Session...");
        // Execute the StatelessSession
        session.execute(Arrays.asList(cashPurchase, debitPurchase, creditPurchase));

        // Assert that the discounts were calculated by the rules
        Assert.assertEquals(0.1, creditPurchase.getDiscount());

        logger.info("===> End of test <===\n");
    }

}
