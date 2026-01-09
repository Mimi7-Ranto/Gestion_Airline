package com.airline.management.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * Generates string identifiers by reading a PostgreSQL sequence and adding a prefix.
 * Configurable parameters:
 *  - sequence: the DB sequence name (required)
 *  - prefix: the string prefix to prepend (optional)
 *  - padding: number of digits to pad (optional, default 6)
 */
public class PostgreSQLTriggerIdGenerator implements IdentifierGenerator, Configurable {

    private String sequenceName;
    private String prefix = "";
    private int padding = 6;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        this.sequenceName = params.getProperty("sequence");
        if (params.getProperty("prefix") != null) this.prefix = params.getProperty("prefix");
        if (params.getProperty("padding") != null) {
            try {
                this.padding = Integer.parseInt(params.getProperty("padding"));
            } catch (NumberFormatException ignored) {
            }
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (sequenceName == null || sequenceName.isBlank()) {
            throw new IllegalStateException("sequence parameter is required for PostgreSQLTriggerIdGenerator");
        }

        Number nextVal = ((Number) session.createNativeQuery("select nextval('" + sequenceName + "')").getSingleResult());
        long val = nextVal.longValue();
        String formatted = String.format("%0" + padding + "d", val);
        return prefix + formatted;
    }
}
