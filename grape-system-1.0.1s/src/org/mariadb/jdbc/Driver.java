/*
MariaDB Client for Java

Copyright (c) 2012-2014 Monty Program Ab.

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

package org.mariadb.jdbc;

import org.mariadb.jdbc.internal.util.exceptions.ExceptionMapper;
import org.mariadb.jdbc.internal.util.constant.Version;
import org.mariadb.jdbc.internal.util.Utils;
import org.mariadb.jdbc.internal.protocol.Protocol;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;


public final class Driver implements java.sql.Driver {

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Could not register driver", e);
        }
    }

    /*
        Provide a "cleanup" method that can be called after unloading driver, to fix Tomcat's obscure classpath handling.

        See CONJ-61.
     */
    public static void unloadDriver() {
        MariaDbStatement.unloadDriver();
    }

    /**
     * Connect to the given connection string.
     * <p>
     * the properties are currently ignored
     *
     * @param url the url to connect to
     * @return a connection
     * @throws SQLException if it is not possible to connect
     */
    public Connection connect(final String url, final Properties props) throws SQLException {

        //log.debug("Connecting to: " + url);
        try {
            UrlParser urlParser = UrlParser.parse(url, props);
            if (urlParser == null) {
                return null;
            }
            if (urlParser.getHostAddresses() == null) {
                //log.info("MariaDB connector : missing Host address");
                return null;
            } else {
                ReentrantLock lock = new ReentrantLock();
                Protocol protocol = Utils.retrieveProxy(urlParser, lock);
                return MariaDbConnection.newConnection(url, protocol, lock);
            }

        } catch (SQLException e) {
            ExceptionMapper.throwException(e, null, null);
            return null;
        }
    }

    /**
     * returns true if the driver can accept the url.
     *
     * @param url the url to test
     * @return true if the url is valid for this driver
     */
    @Override
    public boolean acceptsURL(String url) {
        return UrlParser.acceptsUrl(url);
    }

    /**
     * get the property info. TODO: not implemented!
     *
     * @param url  the url to get properties for
     * @param info the info props
     * @return something - not implemented
     * @throws SQLException if there is a problem getting the property info
     */
    public DriverPropertyInfo[] getPropertyInfo(String url,
                                                Properties info)
            throws SQLException {
        return new DriverPropertyInfo[0];
    }

    /**
     * gets the major version of the driver.
     *
     * @return the major versions
     */
    public int getMajorVersion() {
        return Version.majorVersion;
    }

    /**
     * gets the minor version of the driver.
     *
     * @return the minor version
     */
    public int getMinorVersion() {
        return Version.minorVersion;
    }

    /**
     * checks if the driver is jdbc compliant (not yet!).
     *
     * @return false since the driver is not compliant
     */
    public boolean jdbcCompliant() {
        return false;
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Use logging parameters for enabling logging.");
    }
}
