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

Copyright (c) 2009-2011, Marcus Eriksson, Trond Norbye, Stephane Giron

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

import org.mariadb.jdbc.internal.com.send.parameters.*;
import org.mariadb.jdbc.internal.com.send.parameters.OffsetTimeParameter;
import org.mariadb.jdbc.internal.com.send.parameters.ZonedDateTimeParameter;
import org.mariadb.jdbc.internal.util.exceptions.ExceptionMapper;
import org.mariadb.jdbc.internal.ColumnType;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class BasePrepareStatement extends MariaDbStatement implements PreparedStatement {

    /**
     * The ISO-like date-time formatter that formats or parses a date-time with
     * offset and zone, such as '2011-12-03T10:15:30+01:00[Europe/Paris]'.
     * and without the 'T' time delimiter
     * <p>This returns an immutable formatter capable of formatting and parsing
     * a format that extends the ISO-8601 extended offset date-time format
     * to add the time-zone.</p>
     **/
    private static final DateTimeFormatter SPEC_ISO_ZONED_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral('T')
            .optionalEnd()
            .optionalStart()
            .appendLiteral(' ')
            .optionalEnd()
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .parseCaseSensitive()
            .appendZoneRegionId()
            .appendLiteral(']')
            .toFormatter();

    protected boolean useFractionalSeconds;
    protected boolean hasLongData = false;


    public BasePrepareStatement(MariaDbConnection connection, int resultSetScrollType) {
        super(connection, resultSetScrollType);
        this.useFractionalSeconds = options.useFractionalSeconds;
    }

    /**
     * Clone cached object.
     *
     * @param connection connection
     * @return BasePrepareStatement
     * @throws CloneNotSupportedException if cloning exception
     */
    public BasePrepareStatement clone(MariaDbConnection connection) throws CloneNotSupportedException {
        BasePrepareStatement base = (BasePrepareStatement) super.clone(connection);
        base.useFractionalSeconds = options.useFractionalSeconds;
        return base;
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        if (executeInternal(getFetchSize())) {
            return 0;
        }
        return getLargeUpdateCount();
    }


    protected abstract boolean executeInternal(int fetchSize) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>Reader</code> object, which is the given number of characters
     * long. When a very large UNICODE value is input to a <code>LONGVARCHAR</code> parameter, it may be more practical
     * to send it via a <code>java.io.Reader</code> object. The data will be read from the stream as needed until
     * end-of-file is reached.  The JDBC driver will do any necessary conversion from UNICODE to the database char
     * format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the Unicode data
     * @param length         the number of characters in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        if (reader == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new ReaderParameter(reader, length, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code> object, which is the given number of characters
     * long. When a very large UNICODE value is input to a <code>LONGVARCHAR</code> parameter, it may be more practical
     * to send it via a <code>java.io.Reader</code> object. The data will be read from the stream as needed until
     * end-of-file is reached.  The JDBC driver will do any necessary conversion from UNICODE to the database char
     * format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the Unicode data
     * @param length         the number of characters in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        if (reader == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new ReaderParameter(reader, length, connection.noBackslashEscapes));
        hasLongData = true;
    }


    /**
     * Sets the designated parameter to the given <code>Reader</code> object. When a very large UNICODE value is input
     * to a <code>LONGVARCHAR</code> parameter, it may be more practical to send it via a <code>java.io.Reader</code>
     * object. The data will be read from the stream as needed until end-of-file is reached.  The JDBC driver will do
     * any necessary conversion from UNICODE to the database char format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface. <P><B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setCharacterStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the Unicode data
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
        if (reader == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new ReaderParameter(reader, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to the given <code>REF(&lt;structured-type&gt;)</code> value. The driver converts
     * this to an SQL <code>REF</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param ref            an SQL <code>REF</code> value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setRef(final int parameterIndex, final Ref ref) throws SQLException {
        throw ExceptionMapper.getFeatureNotSupportedException("REF not supported");
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Blob</code> object. The driver converts this to an SQL
     * <code>BLOB</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param blob           a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setBlob(final int parameterIndex, final Blob blob) throws SQLException {
        if (blob == null) {
            setNull(parameterIndex, Types.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(blob.getBinaryStream(), blob.length(), connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The inputstream must contain  the number of
     * characters specified by length otherwise a <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the <code>setBinaryStream (int, InputStream,
     * int)</code> method because it informs the driver that the parameter value should be sent to the server as a
     * <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used, the driver may have to do extra work to
     * determine whether the parameter data should be sent to the server as a <code>LONGVARBINARY</code> or a
     * <code>BLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param inputStream    An object that contains the data to set the parameter value to.
     * @param length         the number of bytes in the parameter data.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code>; if the length specified is less than zero or if the
     *                                         number of bytes in the inputstream does not match the specfied length.
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
        if (inputStream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(inputStream, length, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to a <code>InputStream</code> object. This method differs from the
     * <code>setBinaryStream (int, InputStream)</code> method because it informs the driver that the parameter value
     * should be sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter data should be sent to the server as a
     * <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * <br>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if it might be more efficient to use a
     * version of <code>setBlob</code> which takes a length parameter.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param inputStream    An object that contains the data to set the parameter value to.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code> or if parameterIndex does not correspond to a
     *                                         parameter marker in the SQL statement,
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
        if (inputStream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }

        setParameter(parameterIndex, new StreamParameter(inputStream, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Clob</code> object. The driver converts this to an SQL
     * <code>CLOB</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param clob           a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.2
     */
    public void setClob(final int parameterIndex, final Clob clob) throws SQLException {
        if (clob == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }

        setParameter(parameterIndex, new ReaderParameter(clob.getCharacterStream(), clob.length(), connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number of characters
     * specified by length otherwise a <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the <code>setCharacterStream (int, Reader,
     * int)</code> method because it informs the driver that the parameter value should be sent to the server as a
     * <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the driver may have to do extra work
     * to determine whether the parameter data should be sent to the server as a <code>LONGVARCHAR</code> or a
     * <code>CLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @param length         the number of characters in the parameter data.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code> or if the length specified is less than zero.
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        setCharacterStream(parameterIndex, reader, length);
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. This method differs from the
     * <code>setCharacterStream (int, Reader)</code> method because it informs the driver that the parameter value
     * should be sent to the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter data should be sent to the server as a
     * <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <br>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if it might be more efficient to use a
     * version of <code>setClob</code> which takes a length parameter.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code>or if parameterIndex does not correspond to a
     *                                         parameter marker in the SQL statement
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
        setCharacterStream(parameterIndex, reader);
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.Array</code> object. The driver converts this to an SQL
     * <code>ARRAY</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param array          an <code>Array</code> object that maps an SQL <code>ARRAY</code> value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setArray(final int parameterIndex, final Array array) throws SQLException {
        throw ExceptionMapper.getFeatureNotSupportedException("Arrays not supported");
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value, using the given
     * <code>Calendar</code> object.  The driver uses the <code>Calendar</code> object to construct an SQL
     * <code>DATE</code> value, which the driver then sends to the database.  With a <code>Calendar</code> object, the
     * driver can calculate the date taking into account a custom timezone.  If no <code>Calendar</code> object is
     * specified, the driver uses the default timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param date           the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use to construct the date
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setDate(final int parameterIndex, final Date date, final Calendar cal) throws SQLException {
        if (date == null) {
            setNull(parameterIndex, Types.DATE);
            return;
        }
        setParameter(parameterIndex, new DateParameter(date, cal != null ? cal.getTimeZone() : TimeZone.getDefault(), protocol.getOptions()));
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value using the default time zone of the
     * virtual machine that is running the application. The driver converts this to an SQL <code>DATE</code> value when
     * it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param date           the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setDate(int parameterIndex, Date date) throws SQLException {
        if (date == null) {
            setNull(parameterIndex, Types.DATE);
            return;
        }
        setParameter(parameterIndex, new DateParameter(date, TimeZone.getDefault(), protocol.getOptions()));
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value, using the given
     * <code>Calendar</code> object.  The driver uses the <code>Calendar</code> object to construct an SQL
     * <code>TIME</code> value, which the driver then sends to the database.  With a <code>Calendar</code> object, the
     * driver can calculate the time taking into account a custom timezone.  If no <code>Calendar</code> object is
     * specified, the driver uses the default timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param time           the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use to construct the time
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setTime(final int parameterIndex, final Time time, final Calendar cal) throws SQLException {
        if (time == null) {
            setNull(parameterIndex, ColumnType.TIME);
            return;
        }
        setParameter(parameterIndex, new TimeParameter(time, cal != null ? cal.getTimeZone() : TimeZone.getDefault(), useFractionalSeconds));
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.
     * the driver uses the default timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param time           the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setTime(final int parameterIndex, final Time time) throws SQLException {
        if (time == null) {
            setNull(parameterIndex, ColumnType.TIME);
            return;
        }
        setParameter(parameterIndex, new TimeParameter(time, TimeZone.getDefault(), useFractionalSeconds));
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value, using the given
     * <code>Calendar</code> object.  The driver uses the <code>Calendar</code> object to construct an SQL
     * <code>TIMESTAMP</code> value, which the driver then sends to the database.  With a <code>Calendar</code> object,
     * the driver can calculate the timestamp taking into account a custom timezone.  If no <code>Calendar</code> object
     * is specified, the driver uses the default timezone, which is that of the virtual machine running the
     * application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param timestamp      the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use to construct the timestamp
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setTimestamp(final int parameterIndex, final Timestamp timestamp, final Calendar cal) throws SQLException {
        if (timestamp == null) {
            setNull(parameterIndex, ColumnType.DATETIME);
            return;
        }
        TimeZone tz = cal != null ? cal.getTimeZone() : protocol.getTimeZone();
        setParameter(parameterIndex, new TimestampParameter(timestamp, tz, useFractionalSeconds));
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value. The driver converts this to an
     * SQL <code>TIMESTAMP</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param timestamp      the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setTimestamp(final int parameterIndex, final Timestamp timestamp) throws SQLException {
        if (timestamp == null) {
            setNull(parameterIndex, ColumnType.DATETIME);
            return;
        }
        setParameter(parameterIndex, new TimestampParameter(timestamp,  protocol.getTimeZone(), useFractionalSeconds));

    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * <br>
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType        the SQL type code defined in <code>java.sql.Types</code>
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is a <code>ARRAY</code>, <code>BLOB</code>,
     *                                         <code>CLOB</code>, <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                                         <code>NCHAR</code>, <code>NCLOB</code>, <code>NVARCHAR</code>,
     *                                         <code>LONGNVARCHAR</code>, <code>REF</code>, <code>ROWID</code>,
     *                                         <code>SQLXML</code> or  <code>STRUCT</code> data type and the JDBC driver does not
     *                                         support this data type
     */
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        setParameter(parameterIndex, new NullParameter());
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * <br>
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param mysqlType      the type code defined in <code> ColumnType</code>
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is a <code>ARRAY</code>, <code>BLOB</code>,
     *                                         <code>CLOB</code>, <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                                         <code>NCHAR</code>, <code>NCLOB</code>, <code>NVARCHAR</code>,
     *                                         <code>LONGNVARCHAR</code>, <code>REF</code>, <code>ROWID</code>,
     *                                         <code>SQLXML</code> or  <code>STRUCT</code> data type and the JDBC driver does not
     *                                         support this data type
     */
    public void setNull(final int parameterIndex, final ColumnType mysqlType) throws SQLException {
        setParameter(parameterIndex, new NullParameter(mysqlType));
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>. This version of the method <code>setNull</code> should be
     * used for user-defined types and REF type parameters.  Examples of user-defined types include: STRUCT, DISTINCT,
     * JAVA_OBJECT, and named array types.
     * <br>
     * <P><B>Note:</B> To be portable, applications must give the SQL type code and the fully-qualified SQL type name
     * when specifying a NULL user-defined or REF parameter.  In the case of a user-defined type the name is the type
     * name of the parameter itself.  For a REF parameter, the name is the type name of the referenced type.  If a JDBC
     * driver does not need the type code or type name information, it may ignore it.
     * <br>
     * Although it is intended for user-defined and Ref parameters, this method may be used to set a null parameter of
     * any JDBC type. If the parameter does not have a user-defined or REF type, the given typeName is ignored.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType        a value from <code>java.sql.Types</code>
     * @param typeName       the fully-qualified name of an SQL user-defined type; ignored if the parameter is not a
     *                       user-defined type or REF
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is a <code>ARRAY</code>, <code>BLOB</code>,
     *                                         <code>CLOB</code>, <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                                         <code>NCHAR</code>, <code>NCLOB</code>, <code>NVARCHAR</code>,
     *                                         <code>LONGNVARCHAR</code>, <code>REF</code>, <code>ROWID</code>,
     *                                         <code>SQLXML</code> or  <code>STRUCT</code> data type and the JDBC driver does not
     *                                         support this data type or if the JDBC driver does not support this method
     * @since 1.2
     */
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        setParameter(parameterIndex, new NullParameter());
    }

    protected abstract void setParameter(final int parameterIndex, final ParameterHolder holder) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> value. The driver converts this to an SQL
     * <code>DATALINK</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param url            the <code>java.net.URL</code> object to be set
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    @Override
    public void setURL(final int parameterIndex, final URL url) throws SQLException {
        if (url == null) {
            setNull(parameterIndex, ColumnType.STRING);
            return;
        }
        setParameter(parameterIndex, new StringParameter(url.toString(), connection.noBackslashEscapes));
    }

    /**
     * Retrieves the number, types and properties of this <code>PreparedStatement</code> object's parameters.
     *
     * @return a <code>ParameterMetaData</code> object that contains information about the number, types and properties
     * for each parameter marker of this <code>PreparedStatement</code> object
     * @throws SQLException if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     * @see ParameterMetaData
     */
    public abstract ParameterMetaData getParameterMetaData() throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The driver converts this to a SQL
     * <code>ROWID</code> value when it sends it to the database
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param rowid          the parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setRowId(final int parameterIndex, final RowId rowid) throws SQLException {
        throw ExceptionMapper.getFeatureNotSupportedException("RowIDs not supported");
    }

    /**
     * Sets the designated paramter to the given <code>String</code> object. The driver converts this to a SQL
     * <code>NCHAR</code> or <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value (depending on the argument's size
     * relative to the driver's limits on <code>NVARCHAR</code> values) when it sends it to the database.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the driver does not support national character sets;  if the driver can detect
     *                                         that a data conversion error could occur; if a database access error occurs; or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setNString(final int parameterIndex, final String value) throws SQLException {
        setString(parameterIndex, value);
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The <code>Reader</code> reads the data till
     * end-of-file is reached. The driver does the necessary conversion from Java character format to the national
     * character set in the database.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @param length         the number of characters in the parameter data.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the driver does not support national character sets;  if the driver can detect
     *                                         that a data conversion error could occur; if a database access error occurs; or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
        setCharacterStream(parameterIndex, value, length);
    }


    /**
     * Sets the designated parameter to a <code>Reader</code> object. The <code>Reader</code> reads the data till
     * end-of-file is reached. The driver does the necessary conversion from Java character format to the national
     * character set in the database.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface. <P><B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setNCharacterStream</code> which takes a length parameter.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the driver does not support national character sets;  if the driver can detect
     *                                         that a data conversion error could occur; if a database access error occurs; or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
        setCharacterStream(parameterIndex, value);
    }

    /**
     * Sets the designated parameter to a <code>java.sql.NClob</code> object. The driver converts this to a SQL
     * <code>NCLOB</code> value when it sends it to the database.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the driver does not support national character sets;  if the driver can detect
     *                                         that a data conversion error could occur; if a database access error occurs; or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setNClob(final int parameterIndex, final java.sql.NClob value) throws SQLException {
        setClob(parameterIndex, value);
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number of characters
     * specified by length otherwise a <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the <code>setCharacterStream (int, Reader,
     * int)</code> method because it informs the driver that the parameter value should be sent to the server as a
     * <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the driver may have to do extra
     * work to determine whether the parameter data should be sent to the server as a <code>LONGNVARCHAR</code> or a
     * <code>NCLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @param length         the number of characters in the parameter data.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the length specified is less than zero; if the driver does not support national
     *                                         character sets; if the driver can detect that a data conversion error could occur;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        setClob(parameterIndex, reader, length);
    }


    /**
     * Sets the designated parameter to a <code>Reader</code> object. This method differs from the
     * <code>setCharacterStream (int, Reader)</code> method because it informs the driver that the parameter value
     * should be sent to the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter data should be sent to the server as a
     * <code>LONGNVARCHAR</code> or a <code>NCLOB</code> <P><B>Note:</B> Consult your JDBC driver documentation to
     * determine if it might be more efficient to use a version of <code>setNClob</code> which takes a length
     * parameter.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if the driver does not support national character sets; if the driver can detect
     *                                         that a data conversion error could occur;  if a database access error occurs or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
        setClob(parameterIndex, reader);
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
     * SQL <code>XML</code> value when it sends it to the database.
     * <br>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param xmlObject      a <code>SQLXML</code> object that maps an SQL <code>XML</code> value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code> or the <code>java.xml.transform.Result</code>,
     *                                         <code>Writer</code> or <code>OutputStream</code> has not been closed for the
     *                                         <code>SQLXML</code> object
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        throw ExceptionMapper.getFeatureNotSupportedException("SQlXML not supported");
    }

    /**
     * <p>Sets the value of the designated parameter with the given object. The second argument must be an object type;
     * for integral values, the <code>java.lang</code> equivalent objects should be used.
     * <br>
     * If the second argument is an <code>InputStream</code> then the stream must contain the number of bytes specified
     * by scaleOrLength.  If the second argument is a <code>Reader</code> then the reader must contain the number of
     * characters specified by scaleOrLength. If these conditions are not true the driver will generate a
     * <code>SQLException</code> when the prepared statement is executed.
     * <br>
     * <p>The given Java object will be converted to the given targetSqlType before being sent to the database.
     * <br>
     * If the object has a custom mapping (is of a class implementing the interface <code>SQLData</code>), the JDBC
     * driver should call the method <code>SQLData.writeSQL</code> to write it to the SQL data stream. If, on the other
     * hand, the object is of a class implementing <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,
     * <code>NClob</code>, <code>Struct</code>, <code>java.net.URL</code>, or <code>Array</code>, the driver should pass
     * it to the database as a value of the corresponding SQL type.
     * <br>
     * <p>Note that this method may be used to pass database-specific abstract data types.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param obj            the object containing the input parameter value
     * @param targetSqlType  the SQL type (as defined in java.sql.Types) to be sent to the database. The scale argument
     *                       may further qualify this type.
     * @param scaleOrLength  for <code>java.sql.Types.DECIMAL</code> or <code>java.sql.Types.NUMERIC types</code>, this
     *                       is the number of digits after the decimal point. For Java Object types
     *                       <code>InputStream</code> and <code>Reader</code>, this is the length of the data in the
     *                       stream or reader.  For all other types, this value will be ignored.
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs; this method is called on a closed
     *                                         <code>PreparedStatement</code> or if the Java Object specified by x is an
     *                                         InputStream or Reader object and the value of the scale parameter is less than
     *                                         zero
     * @throws SQLFeatureNotSupportedException if <code>targetSqlType</code> is a <code>ARRAY</code>, <code>BLOB</code>,
     *                                         <code>CLOB</code>, <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                                         <code>NCHAR</code>, <code>NCLOB</code>, <code>NVARCHAR</code>,
     *                                         <code>LONGNVARCHAR</code>, <code>REF</code>, <code>ROWID</code>,
     *                                         <code>SQLXML</code> or  <code>STRUCT</code> data type and the JDBC driver does not
     *                                         support this data type
     * @see Types
     */
    public void setObject(final int parameterIndex, final Object obj, final int targetSqlType, final int scaleOrLength) throws SQLException {
        setInternalObject(parameterIndex, obj, targetSqlType, scaleOrLength);
    }

    /**
     * Sets the value of the designated parameter with the given object. This method is like the method
     * <code>setObject</code> above, except that it assumes a scale of zero.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param obj            the object containing the input parameter value
     * @param targetSqlType  the SQL type (as defined in java.sql.Types) to be sent to the database
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>targetSqlType</code> is a <code>ARRAY</code>, <code>BLOB</code>,
     *                                         <code>CLOB</code>, <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                                         <code>NCHAR</code>, <code>NCLOB</code>, <code>NVARCHAR</code>,
     *                                         <code>LONGNVARCHAR</code>, <code>REF</code>, <code>ROWID</code>,
     *                                         <code>SQLXML</code> or  <code>STRUCT</code> data type and the JDBC driver does not
     *                                         support this data type
     * @see Types
     */
    public void setObject(final int parameterIndex, final Object obj, final int targetSqlType) throws SQLException {
        setInternalObject(parameterIndex, obj, targetSqlType, Long.MAX_VALUE);
    }

    /**
     * <p>Sets the value of the designated parameter using the given object. The second parameter must be of type
     * <code>Object</code>; therefore, the <code>java.lang</code> equivalent objects should be used for built-in types.
     * <br>
     * <p>The JDBC specification specifies a standard mapping from Java <code>Object</code> types to SQL types.  The
     * given argument will be converted to the corresponding SQL type before being sent to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param obj            the object containing the input parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs; this method is called on a closed
     *                      <code>PreparedStatement</code> or the type of the given object is ambiguous
     */
    public void setObject(final int parameterIndex, final Object obj) throws SQLException {
        if (obj == null) {
            setNull(parameterIndex, Types.INTEGER);
        } else if (obj instanceof String) {
            setString(parameterIndex, (String) obj);
        } else if (obj instanceof Integer) {
            setInt(parameterIndex, (Integer) obj);
        } else if (obj instanceof Long) {
            setLong(parameterIndex, (Long) obj);
        } else if (obj instanceof Short) {
            setShort(parameterIndex, (Short) obj);
        } else if (obj instanceof Double) {
            setDouble(parameterIndex, (Double) obj);
        } else if (obj instanceof Float) {
            setFloat(parameterIndex, (Float) obj);
        } else if (obj instanceof Byte) {
            setByte(parameterIndex, (Byte) obj);
        } else if (obj instanceof byte[]) {
            setBytes(parameterIndex, (byte[]) obj);
        } else if (obj instanceof Date) {
            setDate(parameterIndex, (Date) obj);
        } else if (obj instanceof Time) {
            setTime(parameterIndex, (Time) obj);
        } else if (obj instanceof Timestamp) {
            setTimestamp(parameterIndex, (Timestamp) obj);
        } else if (obj instanceof java.util.Date) {
            setTimestamp(parameterIndex, new Timestamp(((java.util.Date) obj).getTime()));
        } else if (obj instanceof Boolean) {
            setBoolean(parameterIndex, (Boolean) obj);
        } else if (obj instanceof Blob) {
            setBlob(parameterIndex, (Blob) obj);
        } else if (obj instanceof InputStream) {
            setBinaryStream(parameterIndex, (InputStream) obj);
        } else if (obj instanceof Reader) {
            setCharacterStream(parameterIndex, (Reader) obj);
        } else if (obj instanceof BigDecimal) {
            setBigDecimal(parameterIndex, (BigDecimal) obj);
        } else if (obj instanceof BigInteger) {
            setString(parameterIndex, obj.toString());
        } else if (obj instanceof Clob) {
            setClob(parameterIndex, (Clob) obj);
        } else if (LocalDateTime.class.isInstance(obj)) {
            setTimestamp(parameterIndex, Timestamp.valueOf(LocalDateTime.class.cast(obj)));
        } else if (Instant.class.isInstance(obj)) {
            setTimestamp(parameterIndex, Timestamp.from(Instant.class.cast(obj)));
        } else if (LocalDate.class.isInstance(obj)) {
            setDate(parameterIndex, Date.valueOf(LocalDate.class.cast(obj)));
        } else if (OffsetDateTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new ZonedDateTimeParameter(
                            OffsetDateTime.class.cast(obj).toZonedDateTime(),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (OffsetTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new OffsetTimeParameter(
                            OffsetTime.class.cast(obj),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (ZonedDateTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new ZonedDateTimeParameter(
                            ZonedDateTime.class.cast(obj),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (LocalTime.class.isInstance(obj)) {
            setTime(parameterIndex, Time.valueOf(LocalTime.class.cast(obj)));
        } else {
            //fallback to sending serialized object
            try {
                setParameter(parameterIndex, new SerializableParameter(obj, connection.noBackslashEscapes));
                hasLongData = true;
            } catch (IOException e) {
                throw ExceptionMapper.getSqlException(
                        "Could not set parameter in setObject, Object class is not handled (Class : " + obj.getClass() + ")");
            }
        }
    }

    @Override
    public void setObject(int parameterIndex, Object obj, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        setObject(parameterIndex, obj, targetSqlType.getVendorTypeNumber(), scaleOrLength);
    }

    @Override
    public void setObject(int parameterIndex, Object obj, SQLType targetSqlType) throws SQLException {
        setObject(parameterIndex, obj, targetSqlType.getVendorTypeNumber());
    }

    protected void setInternalObject(final int parameterIndex, final Object obj, final int targetSqlType,
                                     final long scaleOrLength) throws SQLException {
        switch (targetSqlType) {
            case Types.ARRAY:
            case Types.DATALINK:
            case Types.JAVA_OBJECT:
            case Types.REF:
            case Types.ROWID:
            case Types.SQLXML:
            case Types.STRUCT:
                throw ExceptionMapper.getFeatureNotSupportedException("Type not supported");
            default:
                break;
        }

        if (obj == null) {
            setNull(parameterIndex, Types.INTEGER);
        } else if (obj instanceof String) {
            if (targetSqlType == Types.BLOB) {
                throw ExceptionMapper.getSqlException("Cannot convert a String to a Blob");
            }
            String str = (String) obj;
            try {
                switch (targetSqlType) {
                    case Types.BIT:
                    case Types.BOOLEAN:
                        setBoolean(parameterIndex, !("false".equalsIgnoreCase(str) || "0".equals(str)));
                        break;
                    case Types.TINYINT:
                        setByte(parameterIndex, Byte.parseByte(str));
                        break;
                    case Types.SMALLINT:
                        setShort(parameterIndex, Short.parseShort(str));
                        break;
                    case Types.INTEGER:
                        setInt(parameterIndex, Integer.parseInt(str));
                        break;
                    case Types.DOUBLE:
                    case Types.FLOAT:
                        setDouble(parameterIndex, Double.valueOf(str));
                        break;
                    case Types.REAL:
                        setFloat(parameterIndex, Float.valueOf(str));
                        break;
                    case Types.BIGINT:
                        setLong(parameterIndex, Long.valueOf(str));
                        break;
                    case Types.DECIMAL:
                    case Types.NUMERIC:
                        setBigDecimal(parameterIndex, new BigDecimal(str));
                        break;
                    case Types.CLOB:
                    case Types.NCLOB:
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.NCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGNVARCHAR:
                        setString(parameterIndex, str);
                        break;
                    case Types.TIMESTAMP:
                        if (str.startsWith("0000-00-00")) {
                            setTimestamp(parameterIndex, null);
                        } else {
                            setTimestamp(parameterIndex, Timestamp.valueOf(str));
                        }
                        break;
                    case Types.TIME:
                        setTime(parameterIndex, Time.valueOf((String) obj));
                        break;
                    case Types.TIME_WITH_TIMEZONE:
                        setParameter(parameterIndex,
                                new OffsetTimeParameter(
                                        OffsetTime.parse(str),
                                        protocol.getTimeZone().toZoneId(),
                                        useFractionalSeconds,
                                        options));
                        break;
                    case Types.TIMESTAMP_WITH_TIMEZONE:

                        setParameter(parameterIndex,
                                new ZonedDateTimeParameter(
                                        ZonedDateTime.parse(str, SPEC_ISO_ZONED_DATE_TIME),
                                        protocol.getTimeZone().toZoneId(),
                                        useFractionalSeconds,
                                        options));
                        break;
                    default:
                        throw ExceptionMapper.getSqlException("Could not convert [" + str + "] to " + targetSqlType);
                }
            } catch (IllegalArgumentException e) {
                throw ExceptionMapper.getSqlException("Could not convert [" + str + "] to " + targetSqlType, e);
            }
        } else if (obj instanceof Number) {
            testNumbers(targetSqlType);
            Number bd = (Number) obj;
            switch (targetSqlType) {
                case Types.TINYINT:
                    setByte(parameterIndex, bd.byteValue());
                    break;
                case Types.SMALLINT:
                    setShort(parameterIndex, bd.shortValue());
                    break;
                case Types.INTEGER:
                    setInt(parameterIndex, bd.intValue());
                    break;
                case Types.BIGINT:
                    setLong(parameterIndex, bd.longValue());
                    break;
                case Types.FLOAT:
                case Types.DOUBLE:
                    setDouble(parameterIndex, bd.doubleValue());
                    break;
                case Types.REAL:
                    setFloat(parameterIndex, bd.floatValue());
                    break;
                case Types.DECIMAL:
                case Types.NUMERIC:
                    if (obj instanceof BigDecimal) {
                        setBigDecimal(parameterIndex, (BigDecimal) obj);
                    } else if (obj instanceof Double || obj instanceof Float) {
                        setDouble(parameterIndex, bd.doubleValue());
                    } else {
                        setLong(parameterIndex, bd.longValue());
                    }
                    break;
                case Types.BIT:
                    setBoolean(parameterIndex, bd.shortValue() != 0);
                    break;
                case Types.CHAR:
                case Types.VARCHAR:
                    setString(parameterIndex, bd.toString());
                    break;
                default:
                    throw ExceptionMapper.getSqlException("Could not convert [" + bd + "] to " + targetSqlType);

            }
        } else if (obj instanceof byte[]) {
            if (targetSqlType == Types.BINARY || targetSqlType == Types.VARBINARY || targetSqlType == Types.LONGVARBINARY) {
                setBytes(parameterIndex, (byte[]) obj);
            } else {
                throw ExceptionMapper.getSqlException("Can only convert a byte[] to BINARY, VARBINARY or LONGVARBINARY");
            }

        } else if (obj instanceof Time) {
            setTime(parameterIndex, (Time) obj);      // it is just a string anyway
        } else if (obj instanceof Timestamp) {
            setTimestamp(parameterIndex, (Timestamp) obj);
        } else if (obj instanceof Date) {
            setDate(parameterIndex, (Date) obj);
        } else if (obj instanceof java.util.Date) {
            long timemillis = ((java.util.Date) obj).getTime();
            if (targetSqlType == Types.DATE) {
                setDate(parameterIndex, new Date(timemillis));
            } else if (targetSqlType == Types.TIME) {
                setTime(parameterIndex, new Time(timemillis));
            } else if (targetSqlType == Types.TIMESTAMP) {
                setTimestamp(parameterIndex, new Timestamp(timemillis));
            }
        } else if (obj instanceof Boolean) {
            testNumbers(targetSqlType);
            setBoolean(parameterIndex, (Boolean) obj);
        } else if (obj instanceof Blob) {
            setBlob(parameterIndex, (Blob) obj);
        } else if (obj instanceof BigInteger) {
            setString(parameterIndex, obj.toString());
        } else if (obj instanceof Clob) {
            setClob(parameterIndex, (Clob) obj);
        } else if (obj instanceof InputStream) {
            setBinaryStream(parameterIndex, (InputStream) obj, scaleOrLength);
        } else if (obj instanceof Reader) {
            setCharacterStream(parameterIndex, (Reader) obj, scaleOrLength);
        } else if (LocalDateTime.class.isInstance(obj)) {
            setTimestamp(parameterIndex, Timestamp.valueOf(LocalDateTime.class.cast(obj)));
        } else if (Instant.class.isInstance(obj)) {
            setTimestamp(parameterIndex, Timestamp.from(Instant.class.cast(obj)));
        } else if (LocalDate.class.isInstance(obj)) {
            setDate(parameterIndex, Date.valueOf(LocalDate.class.cast(obj)));
        } else if (OffsetDateTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new ZonedDateTimeParameter(
                            OffsetDateTime.class.cast(obj).toZonedDateTime(),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (OffsetTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new OffsetTimeParameter(
                            OffsetTime.class.cast(obj),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (ZonedDateTime.class.isInstance(obj)) {
            setParameter(parameterIndex,
                    new ZonedDateTimeParameter(
                            ZonedDateTime.class.cast(obj),
                            protocol.getTimeZone().toZoneId(),
                            useFractionalSeconds,
                            options));
        } else if (LocalTime.class.isInstance(obj)) {
            setTime(parameterIndex, Time.valueOf(LocalTime.class.cast(obj)));
        } else {
            throw ExceptionMapper.getSqlException("Could not set parameter in setObject, could not convert: " + obj.getClass() + " to "
                    + targetSqlType);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have the specified number of bytes. When a
     * very large ASCII value is input to a <code>LONGVARCHAR</code> parameter, it may be more practical to send it via
     * a <code>java.io.InputStream</code>. Data will be read from the stream as needed until end-of-file is reached. The
     * JDBC driver will do any necessary conversion from ASCII to the database char format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the Java input stream that contains the ASCII parameter value
     * @param length         the number of bytes in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setAsciiStream(final int parameterIndex, final InputStream stream, final long length) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, length, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * This function reads up the entire stream and stores it in memory since we need to know the length when sending it
     * to the server use the corresponding method with a length parameter if memory is an issue
     * <br>
     * Sets the designated parameter to the given input stream. When a very large ASCII value is input to a
     * <code>LONGVARCHAR</code> parameter, it may be more practical to send it via a <code>java.io.InputStream</code>.
     * Data will be read from the stream as needed until end-of-file is reached.  The JDBC driver will do any necessary
     * conversion from ASCII to the database char format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface. <P><B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setAsciiStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the Java input stream that contains the ASCII parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setAsciiStream(final int parameterIndex, final InputStream stream) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to the given input stream, which will have the specified number of bytes. When a
     * very large ASCII value is input to a <code>LONGVARCHAR</code> parameter, it may be more practical to send it via
     * a <code>java.io.InputStream</code>. Data will be read from the stream as needed until end-of-file is reached. The
     * JDBC driver will do any necessary conversion from ASCII to the database char format.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the Java input stream that contains the ASCII parameter value
     * @param length         the number of bytes in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setAsciiStream(final int parameterIndex, final InputStream stream, final int length) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, length, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * Sets the designated parameter to the given input stream, which will have the specified number of bytes. When a
     * very large binary value is input to a <code>LONGVARBINARY</code> parameter, it may be more practical to send it
     * via a <code>java.io.InputStream</code> object. The data will be read from the stream as needed until end-of-file
     * is reached.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the java input stream which contains the binary parameter value
     * @param length         the number of bytes in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setBinaryStream(final int parameterIndex, final InputStream stream, final long length) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, length, connection.noBackslashEscapes));
        hasLongData = true;
    }

    /**
     * This function reads up the entire stream and stores it in memory since we need to know the length when sending it
     * to the server
     * <br>
     * Sets the designated parameter to the given input stream. When a very large binary value is input to a
     * <code>LONGVARBINARY</code> parameter, it may be more practical to send it via a <code>java.io.InputStream</code>
     * object. The data will be read from the stream as needed until end-of-file is reached.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface. <P><B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setBinaryStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the java input stream which contains the binary parameter value
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setBinaryStream(final int parameterIndex, final InputStream stream) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, connection.noBackslashEscapes));
        hasLongData = true;
    }


    /**
     * Sets the designated parameter to the given input stream, which will have the specified number of bytes. When a
     * very large binary value is input to a <code>LONGVARBINARY</code> parameter, it may be more practical to send it
     * via a <code>java.io.InputStream</code> object. The data will be read from the stream as needed until end-of-file
     * is reached.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param stream         the java input stream which contains the binary parameter value
     * @param length         the number of bytes in the stream
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setBinaryStream(final int parameterIndex, final InputStream stream, final int length) throws SQLException {
        if (stream == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(stream, length, connection.noBackslashEscapes));
        hasLongData = true;
    }


    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter
     *                      marker in the SQL statement;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>PreparedStatement</code>
     */
    public void setBoolean(final int parameterIndex, final boolean value) throws SQLException {
        setParameter(parameterIndex, new ByteParameter(value ? (byte) 1 : (byte) 0));
    }

    /**
     * Sets the designated parameter to the given Java <code>byte</code> value. The driver converts this to an SQL
     * <code>TINYINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param bit            the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setByte(final int parameterIndex, final byte bit) throws SQLException {
        setParameter(parameterIndex, new ByteParameter(bit));
    }

    /**
     * Sets the designated parameter to the given Java <code>short</code> value. The driver converts this to an SQL
     * <code>SMALLINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setShort(final int parameterIndex, final short value) throws SQLException {
        setParameter(parameterIndex, new ShortParameter(value));
    }

    /**
     * Set string parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param str            String
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setString(final int parameterIndex, final String str) throws SQLException {
        if (str == null) {
            setNull(parameterIndex, ColumnType.VARCHAR);
            return;
        }

        setParameter(parameterIndex, new StringParameter(str, connection.noBackslashEscapes));
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.  The driver converts this to an SQL
     * <code>VARBINARY</code> or <code>LONGVARBINARY</code> (depending on the argument's size relative to the driver's
     * limits on <code>VARBINARY</code> values) when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param bytes          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setBytes(final int parameterIndex, final byte[] bytes) throws SQLException {
        if (bytes == null) {
            setNull(parameterIndex, ColumnType.BLOB);
            return;
        }

        setParameter(parameterIndex, new ByteArrayParameter(bytes, connection.noBackslashEscapes));
    }


    /**
     * Sets the designated parameter to the given input stream, which will have the specified number of bytes.
     * <br>
     * When a very large Unicode value is input to a <code>LONGVARCHAR</code> parameter, it may be more practical to
     * send it via a <code>java.io.InputStream</code> object. The data will be read from the stream as needed until
     * end-of-file is reached.  The JDBC driver will do any necessary conversion from Unicode to the database char
     * format.
     * <br>
     * The byte format of the Unicode stream must be a Java UTF-8, as defined in the Java Virtual Machine
     * Specification.
     * <br>
     * <P><B>Note:</B> This stream object can either be a standard Java stream object or your own subclass that
     * implements the standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              a <code>java.io.InputStream</code> object that contains the Unicode parameter value
     * @param length         the number of bytes in the stream
     * @throws SQLException                    if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                                         if a database access error occurs or this method is called on a closed
     *                                         <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @deprecated
     */
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        if (x == null) {
            setNull(parameterIndex, Types.BLOB);
            return;
        }
        setParameter(parameterIndex, new StreamParameter(x, length, connection.noBackslashEscapes));
        hasLongData = true;
    }


    private void testNumbers(int targetSqlType) throws SQLException {
        switch (targetSqlType) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
            case Types.BLOB:
                throw ExceptionMapper.getSqlException("Cannot convert to " + targetSqlType);
            default:
                return;
        }
    }


    public void setInt(final int column, final int value) throws SQLException {
        setParameter(column, new IntParameter(value));
    }

    /**
     * Sets the designated parameter to the given Java <code>long</code> value. The driver converts this to an SQL
     * <code>BIGINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setLong(final int parameterIndex, final long value) throws SQLException {
        setParameter(parameterIndex, new LongParameter(value));
    }

    /**
     * Sets the designated parameter to the given Java <code>float</code> value. The driver converts this to an SQL
     * <code>REAL</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setFloat(final int parameterIndex, final float value) throws SQLException {
        setParameter(parameterIndex, new FloatParameter(value));
    }

    /**
     * Sets the designated parameter to the given Java <code>double</code> value. The driver converts this to an SQL
     * <code>DOUBLE</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setDouble(final int parameterIndex, final double value) throws SQLException {
        setParameter(parameterIndex, new DoubleParameter(value));
    }

    /**
     * Sets the designated parameter to the given <code>java.math.BigDecimal</code> value. The driver converts this to
     * an SQL <code>NUMERIC</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param bigDecimal     the parameter value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed
     *                      <code>PreparedStatement</code>
     */
    public void setBigDecimal(final int parameterIndex, final BigDecimal bigDecimal) throws SQLException {
        if (bigDecimal == null) {
            setNull(parameterIndex, ColumnType.DECIMAL);
            return;
        }

        setParameter(parameterIndex, new BigDecimalParameter(bigDecimal));
    }

}
