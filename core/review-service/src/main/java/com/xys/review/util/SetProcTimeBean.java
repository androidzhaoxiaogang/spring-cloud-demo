package com.xys.review.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@RefreshScope
@Component
public class SetProcTimeBean {

    private static final Logger LOG = LoggerFactory.getLogger(SetProcTimeBean.class);

    private int minMs;

    private int maxMs;

    @Value("${service.defaultMinMs:0}")
    public void setMinMs(int minMs) {
        LOG.info("Set min response time to {} ms.", minMs);
        this.minMs = minMs;
    }

    @Value("${service.defaultMaxMs:0}")
    public void setMaxMs(int maxMs) {
        LOG.info("Set max response time to {} ms.", maxMs);
        this.maxMs = maxMs;
    }

    public void setDefaultProcessingTime(int minMs, int maxMs) {

        if (minMs < 0) {
            minMs = 0;
        }
        if (maxMs < minMs) {
            maxMs = minMs;
        }

        this.minMs = minMs;
        this.maxMs = maxMs;
        LOG.info("Set response time to {} - {} ms.", this.minMs, this.maxMs);
    }

    public int calculateProcessingTime() {
        int processingTimeMs = minMs + (int) (Math.random() * (maxMs - minMs));
        LOG.debug("Return calculated processing time: {} ms", processingTimeMs);
        return processingTimeMs;
    }

}