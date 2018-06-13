/*
MariaDB Client for Java

Copyright (c) 2012-2014 Monty Program Ab.
Copyright (c) 2015-2016 MariaDB Ab.

This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
for more details.

You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to Monty Program Ab info@montyprogram.com.

This particular MariaDB Client for Java file is work
derived from a Drizzle-JDBC. Drizzle-JDBC file which is covered by subject to
the following copyright and notice provisions:

Copyright (c) 2009-2011, Marcus Eriksson

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of the driver nor the names of its contributors may not be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS  AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
OF SUCH DAMAGE.
*/

package org.mariadb.jdbc.internal.protocol;

import org.mariadb.jdbc.internal.ColumnType;
import org.mariadb.jdbc.internal.com.send.ComStmtPrepare;
import org.mariadb.jdbc.internal.com.send.parameters.ParameterHolder;
import org.mariadb.jdbc.internal.com.read.dao.Results;
import org.mariadb.jdbc.internal.io.output.PacketOutputStream;
import org.mariadb.jdbc.internal.util.BulkStatus;
import org.mariadb.jdbc.internal.util.dao.ClientPrepareResult;
import org.mariadb.jdbc.internal.util.dao.PrepareResult;
import org.mariadb.jdbc.internal.util.dao.ServerPrepareResult;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.mariadb.jdbc.internal.util.SqlStates.CONNECTION_EXCEPTION;
import static org.mariadb.jdbc.internal.util.SqlStates.INTERRUPTED_EXCEPTION;

public abstract class AbstractMultiSend {

    protected int statementId = -1;
    protected ColumnType[] parameterTypeHeader;
    private Protocol protocol;
    private PacketOutputStream writer;
    private Results results;
    private List<ParameterHolder[]> parametersList;
    private PrepareResult prepareResult;
    private List<String> queries;
    private boolean binaryProtocol;
    private boolean readPrepareStmtResult;
    private String sql;

    /**
     * Bulk execute for Server PreparedStatement.executeBatch (when no COM_MULTI)
     *
     * @param protocol              protocol
     * @param writer                outputStream
     * @param results               query results
     * @param serverPrepareResult   Prepare result
     * @param parametersList        parameters
     * @param readPrepareStmtResult must execute prepare result
     * @param sql                   sql query.
     */
    public AbstractMultiSend(Protocol protocol, PacketOutputStream writer, Results results, ServerPrepareResult serverPrepareResult,
                             List<ParameterHolder[]> parametersList, boolean readPrepareStmtResult, String sql) {
        this.protocol = protocol;
        this.writer = writer;
        this.results = results;
        this.prepareResult = serverPrepareResult;
        this.parametersList = parametersList;
        this.binaryProtocol = true;
        this.readPrepareStmtResult = readPrepareStmtResult;
        this.sql = sql;
    }

    /**
     * Bulk execute for client-sier PreparedStatement.executeBatch (no prepare).
     *
     * @param protocol            current protocol
     * @param writer              outputStream
     * @param results             results
     * @param clientPrepareResult clientPrepareResult
     * @param parametersList      parameters
     */
    public AbstractMultiSend(Protocol protocol, PacketOutputStream writer, Results results,
                             final ClientPrepareResult clientPrepareResult, List<ParameterHolder[]> parametersList) {
        this.protocol = protocol;
        this.writer = writer;
        this.results = results;
        this.prepareResult = clientPrepareResult;
        this.parametersList = parametersList;
        this.binaryProtocol = false;
        this.readPrepareStmtResult = false;
    }

    /**
     * Bulk execute for statement.executeBatch().
     *
     * @param protocol            protocol
     * @param writer              outputStream
     * @param results             results
     * @param queries             query list
     */
    public AbstractMultiSend(Protocol protocol, PacketOutputStream writer, Results results, List<String> queries) {
        this.protocol = protocol;
        this.writer = writer;
        this.results = results;
        this.queries = queries;
        this.binaryProtocol = false;
        this.readPrepareStmtResult = false;
    }

    public abstract void sendCmd(PacketOutputStream writer, Results results,
                                 List<ParameterHolder[]> parametersList, List<String> queries, int paramCount, BulkStatus status,
                                 PrepareResult prepareResult) throws SQLException, IOException;

    public abstract SQLException handleResultException(SQLException qex, Results results,
                                                         List<ParameterHolder[]> parametersList, List<String> queries, int currentCounter,
                                                         int sendCmdCounter, int paramCount, PrepareResult prepareResult)
            throws SQLException;

    public abstract int getParamCount();

    public abstract int getTotalExecutionNumber();


    public PrepareResult getPrepareResult() {
        return prepareResult;
    }

    /**
     * Execute Bulk execution (send packets by batch of  useBatchMultiSendNumber or when max packet is reached) before reading results.
     *
     * @return prepare result
     * @throws SQLException if any error occur
     */
    public PrepareResult executeBatch() throws SQLException {
        int paramCount = getParamCount();
        if (binaryProtocol) {
            if (readPrepareStmtResult) {
                parameterTypeHeader = new ColumnType[paramCount];
                if (prepareResult == null && protocol.getOptions().cachePrepStmts) {
                    String key = new StringBuilder(protocol.getDatabase()).append("-").append(sql).toString();
                    prepareResult = protocol.prepareStatementCache().get(key);
                    if (prepareResult != null && !((ServerPrepareResult) prepareResult).incrementShareCounter()) {
                        //in cache but been de-allocated
                        prepareResult = null;
                    }
                }
                statementId = (prepareResult == null) ? -1 : ((ServerPrepareResult) prepareResult).getStatementId();
            } else if (prepareResult != null) {
                statementId = ((ServerPrepareResult) prepareResult).getStatementId();
            }
        }
        return executeBatchStandard(paramCount);
    }

    /**
     * Execute Bulk execution (send packets by batch of  useBatchMultiSendNumber or when max packet is reached) before reading results.
     *
     * @param paramCount parameter counter
     * @return prepare result
     * @throws SQLException if any error occur
     */
    private PrepareResult executeBatchStandard(int paramCount) throws SQLException {
        int totalExecutionNumber = getTotalExecutionNumber();
        SQLException exception = null;
        BulkStatus status = new BulkStatus();

        ComStmtPrepare comStmtPrepare = null;
        FutureTask<AsyncMultiReadResult> futureReadTask = null;

        int requestNumberByBulk;
        try {
            do {
                status.sendEnded = false;
                status.sendSubCmdCounter = 0;
                requestNumberByBulk = Math.min(totalExecutionNumber - status.sendCmdCounter, protocol.getOptions().useBatchMultiSendNumber);
                protocol.changeSocketTcpNoDelay(false); //enable NAGLE algorithm temporary.

                //add prepare sub-command
                if (readPrepareStmtResult && prepareResult == null) {

                    comStmtPrepare = new ComStmtPrepare(protocol, sql);
                    comStmtPrepare.send(writer);

                    //read prepare result
                    prepareResult = comStmtPrepare.read(protocol.getReader(), protocol.isEofDeprecated());
                    statementId = ((ServerPrepareResult) prepareResult).getStatementId();
                    paramCount = getParamCount();
                }

                for (; status.sendSubCmdCounter < requestNumberByBulk; ) {
                    sendCmd(writer, results, parametersList, queries, paramCount, status, prepareResult);
                    status.sendSubCmdCounter++;
                    status.sendCmdCounter++;

                    if (futureReadTask == null) {
                        futureReadTask = new FutureTask<>(new AsyncMultiRead(comStmtPrepare, status,
                                protocol, false, this, paramCount,
                                binaryProtocol, results, parametersList, queries, prepareResult));
                        AbstractQueryProtocol.readScheduler.execute(futureReadTask);
                    }
                }
                status.sendEnded = true;

                protocol.changeSocketTcpNoDelay(protocol.getOptions().tcpNoDelay);
                try {
                    AsyncMultiReadResult asyncMultiReadResult = futureReadTask.get();

                    if (binaryProtocol && prepareResult == null && asyncMultiReadResult.getPrepareResult() != null) {
                        prepareResult = asyncMultiReadResult.getPrepareResult();
                        statementId = ((ServerPrepareResult) prepareResult).getStatementId();
                        paramCount = prepareResult.getParamCount();
                    }

                    if (asyncMultiReadResult.getException() != null) {
                        if (((readPrepareStmtResult && prepareResult == null) || !protocol.getOptions().continueBatchOnError)) {
                            throw asyncMultiReadResult.getException();
                        } else {
                            exception = asyncMultiReadResult.getException();
                        }
                    }
                } catch (ExecutionException executionException) {
                    if (executionException.getCause() == null) {
                        throw new SQLException("Error reading results " + executionException.getMessage());
                    }
                    throw new SQLException("Error reading results " + executionException.getCause().getMessage());
                } catch (InterruptedException interruptedException) {
                    protocol.setActiveFutureTask(futureReadTask);
                    Thread.currentThread().interrupt();
                    throw new SQLException("Interrupted awaiting response ", INTERRUPTED_EXCEPTION.getSqlState(), interruptedException);
                } finally {
                    //bulk can prepare, and so if prepare cache is enable, can replace an already cached prepareStatement
                    //this permit to release those old prepared statement without conflict.
                    protocol.forceReleaseWaitingPrepareStatement();
                }

                futureReadTask = null;

            } while (status.sendCmdCounter < totalExecutionNumber);

            if (exception != null) throw exception;

            return prepareResult;

        } catch (IOException e) {
            throw protocol.handleIoException(e);
        }

    }

}
