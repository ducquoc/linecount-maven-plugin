package net.ducquoc.tr.maven.plugin.linecount.engine.parser.catchcli;

import java.util.Map;

/**
 * Catch command.
 *
 * @since 2.0
 */
public class CatchCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("catch")) {
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

        return (String) contextHolder.get("catch");
    }

}
