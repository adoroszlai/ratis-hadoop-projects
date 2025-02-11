/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ratis.protocol;

import org.apache.ratis.util.JavaUtils;

public final class LeaderElectionRequest extends RaftClientRequest{
  public abstract static class Op {

  }
  public static class Pause extends Op {
    @Override
    public String toString() {
      return JavaUtils.getClassSimpleName(getClass()) + ":" ;
    }
  }

  public static class Resume extends Op {
    @Override
    public String toString() {
      return JavaUtils.getClassSimpleName(getClass()) + ":" ;
    }
  }

  public static LeaderElectionRequest newPause(ClientId clientId,
      RaftPeerId serverId, RaftGroupId groupId, long callId, long timeoutMs) {
    return new LeaderElectionRequest(clientId,
        serverId, groupId, callId, timeoutMs,new LeaderElectionRequest.Pause());
  }

  public static LeaderElectionRequest newResume(ClientId clientId,
      RaftPeerId serverId, RaftGroupId groupId, long callId, long timeoutMs) {
    return new LeaderElectionRequest(clientId,
        serverId, groupId, callId, timeoutMs,new LeaderElectionRequest.Resume());
  }

  private final Op op;

  public LeaderElectionRequest(
      ClientId clientId, RaftPeerId serverId, RaftGroupId groupId, long callId, long timeoutMs, Op op) {
    super(clientId, serverId, groupId, callId, readRequestType(), timeoutMs);
    this.op = op;
  }

  public Pause getPause() {
    return op instanceof Pause ? (Pause) op: null;
  }

  public Resume getResume() {
    return op instanceof Resume ? (Resume) op : null;
  }


  @Override
  public String toString() {
    return super.toString() + ", " + op;
  }
}
