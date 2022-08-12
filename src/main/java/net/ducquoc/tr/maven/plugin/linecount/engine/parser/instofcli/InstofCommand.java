package net.ducquoc.tr.maven.plugin.linecount.engine.parser.instofcli;

import java.util.Map;

/**
 * InstanceOf command.
 *
 * @since 2.0
 */
public class InstofCommand {

    /**
     * Matches input str txt.
     */
    public boolean match(String txt) {
        if (txt != null && txt.trim().contains("instanceof")) {
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

        return (String) contextHolder.get("instanceof");
    }

}
