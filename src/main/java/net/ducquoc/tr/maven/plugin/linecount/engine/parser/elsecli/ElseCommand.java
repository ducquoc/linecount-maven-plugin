package net.ducquoc.tr.maven.plugin.linecount.engine.parser.elsecli;

import java.util.Map;

/**
 * Else command.
 *
 * @since 2.0
 */
public class ElseCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("else")) {
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

        return (String) contextHolder.get("else");
    }

}
