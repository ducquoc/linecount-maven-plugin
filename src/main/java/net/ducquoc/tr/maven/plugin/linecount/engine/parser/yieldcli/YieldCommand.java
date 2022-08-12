package net.ducquoc.tr.maven.plugin.linecount.engine.parser.yieldcli;

import java.util.Map;

/**
 * Yield command. (Java 13 - async generator JS/Dart)
 *
 * @since 2.0
 */
public class YieldCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("yield")) {
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

        return (String) contextHolder.get("yield");
    }

}
