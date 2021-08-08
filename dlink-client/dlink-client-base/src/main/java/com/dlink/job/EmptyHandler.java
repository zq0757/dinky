package com.dlink.job;

/**
 * Job2MysqlHandler
 *
 * @author wenmo
 * @since 2021/6/27 0:04
 */
public class EmptyHandler implements JobHandler {

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean ready() {
        return true;
    }

    @Override
    public boolean running() {
        return true;
    }

    @Override
    public boolean success() {
        return true;
    }

    @Override
    public boolean failed() {
        return true;
    }

    @Override
    public boolean callback() {
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }
}
