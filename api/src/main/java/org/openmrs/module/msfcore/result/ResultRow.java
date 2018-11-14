package org.openmrs.module.msfcore.result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResultRow implements Map<String, ResultColumn> {

    // result row: key (localised message property), value
    private Map<String, ResultColumn> internalResultRowMap = new HashMap<String, ResultColumn>();

    @Override
    public int size() {
        return internalResultRowMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalResultRowMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return internalResultRowMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internalResultRowMap.containsKey(value);
    }

    @Override
    public ResultColumn get(Object key) {
        return internalResultRowMap.get(key);
    }

    @Override
    public ResultColumn put(String key, ResultColumn value) {
        return internalResultRowMap.put(key, value);
    }

    @Override
    public ResultColumn remove(Object key) {
        return internalResultRowMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ResultColumn> m) {
        internalResultRowMap.putAll(m);
    }

    @Override
    public void clear() {
        internalResultRowMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return internalResultRowMap.keySet();
    }

    @Override
    public Collection<ResultColumn> values() {
        return internalResultRowMap.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, ResultColumn>> entrySet() {
        return internalResultRowMap.entrySet();
    }

}
