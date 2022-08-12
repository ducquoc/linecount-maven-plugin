package net.ducquoc.tr.maven.plugin.linecount.engine.parser.recordcli;

import java.util.Map;

/**
 * Record command. (Java 14 - Lombok @Data Google @AutoValue)
 *
 * @since 2.0
 */
public class RecordCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("record")) {
            return true;
        }
        return false;
    }

    /**
     * Internal Resolve value.
     *
     * @param resolvedValue
     * @param contextHolder
     * @return
     */
    protected String internalResolve(Object resolvedValue, Map<String, Object> contextHolder) {

        if (resolvedValue == null) {
            return null;
        }

        return (String) contextHolder.get("record");
    }

}
