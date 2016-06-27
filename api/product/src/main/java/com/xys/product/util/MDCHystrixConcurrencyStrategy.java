package com.xys.product.util;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

import java.util.concurrent.Callable;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
public class MDCHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return new MDCHystrixContextCallable<>(callable);
    }

}
