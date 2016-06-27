package com.xys.product.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
public class MDCHystrixContextCallable<K> implements Callable {

    private static final Logger LOG = LoggerFactory.getLogger(MDCHystrixContextCallable.class);

    private final Callable<K> actual;
    private final Map parentMDC;

    public MDCHystrixContextCallable(Callable<K> actual) {
        LOG.debug("Init MDCHystrixContextCallable...");
        this.actual = actual;
        this.parentMDC = MDC.getCopyOfContextMap();
    }

    @Override
    public K call() throws Exception {
        LOG.debug("Call using MDCHystrixContextCallable...");
        Map childMDC = MDC.getCopyOfContextMap();

        try {
            MDC.setContextMap(parentMDC);
            return actual.call();
        } finally {
            if (childMDC == null) {
                LOG.debug("Call done. ChildMDC is null so we clear the MDC.");
                MDC.clear();
            } else {
                LOG.debug("Call done. Reset MDC to the ChildMDC.");
                MDC.setContextMap(childMDC);
            }
        }
    }

}
