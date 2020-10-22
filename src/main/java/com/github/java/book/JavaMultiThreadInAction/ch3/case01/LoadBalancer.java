package com.github.java.book.JavaMultiThreadInAction.ch3.case01;

/**
 * @author pengfei.zhao
 * @date 2020/10/21 19:08
 */
public interface LoadBalancer {
    void updateCandidate(final Candidate candidate);

    Endpoint nextEndpoint();
}
