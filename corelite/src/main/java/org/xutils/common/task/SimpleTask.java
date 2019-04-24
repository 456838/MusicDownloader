package org.xutils.common.task;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 15:57
 * ModifyTime: 15:57
 * Description:
 */
public abstract class SimpleTask<ResultType> extends AbsTask<ResultType> {

    @Override
    protected void onSuccess(ResultType result) {

    }

    @Override
    protected void onError(Throwable ex, boolean isCallbackError) {

    }
}
