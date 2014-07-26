/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.cluster.raft;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.concurrent.atomic.AtomicLong;

public class MockRaftActorContext implements RaftActorContext {

    private String id;
    private ActorSystem system;
    private ActorRef actor;

    public MockRaftActorContext(){

    }

    public MockRaftActorContext(String id, ActorSystem system, ActorRef actor){
        this.id = id;
        this.system = system;
        this.actor = actor;
    }

    @Override public ActorRef actorOf(Props props) {
        return system.actorOf(props);
    }

    @Override public ActorSelection actorSelection(String path) {
        return system.actorSelection(path);
    }

    @Override public String getId() {
        return id;
    }

    @Override public ActorRef getActor() {
        return actor;
    }

    @Override public ElectionTerm getTermInformation() {
        return new ElectionTermImpl(this.id);
    }

    @Override public AtomicLong getCommitIndex() {
        throw new UnsupportedOperationException("getCommitIndex");
    }

    @Override public AtomicLong getLastApplied() {
        throw new UnsupportedOperationException("getLastApplied");
    }

    @Override public ReplicatedLog getReplicatedLog() {
        return new ReplicatedLog(){

            @Override public ReplicatedLogEntry getReplicatedLogEntry(
                long index) {
                throw new UnsupportedOperationException(
                    "getReplicatedLogEntry");
            }

            @Override public ReplicatedLogEntry last() {
                return new ReplicatedLogEntry() {
                    @Override public Object getData() {
                        return null;
                    }

                    @Override public long getTerm() {
                        return 1;
                    }

                    @Override public long getIndex() {
                        return 1;
                    }
                };
            }
        };
    }
}