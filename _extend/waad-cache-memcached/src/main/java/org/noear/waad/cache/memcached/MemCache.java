package org.noear.waad.cache.memcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.noear.waad.cache.ICacheService;
import org.noear.waad.util.EncryptUtils;
import org.noear.waad.util.StrUtils;

import java.lang.reflect.Type;
import java.util.Properties;

public class MemCache implements ICacheService {
    private String _cacheKeyHead;
    private int _defaultSeconds;

    private MemcachedClient _cache = null;

    public MemCache(String keyHeader, int defSeconds, String server, String user, String password) {
        Properties prop = new Properties();
        prop.setProperty("server", server);

        if (user != null) {
            prop.setProperty("user", user);
        }

        if (password != null) {
            prop.setProperty("password", password);
        }

        initDo(prop, keyHeader, defSeconds);
    }

    public MemCache(Properties prop) {
        initDo(prop, prop.getProperty("keyHeader"), 0);
    }

    public MemCache(Properties prop, String keyHeader, int defSeconds) {
        initDo(prop, keyHeader, defSeconds);
    }

    private void initDo(Properties prop, String keyHeader, int defSeconds) {
        String server = prop.getProperty("server");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        if (defSeconds == 0) {
            String defSeconds_str = prop.getProperty("defSeconds");
            defSeconds = (defSeconds_str == null ? 60 * 1 : Integer.parseInt(defSeconds_str));
        }

        _cacheKeyHead = keyHeader;
        _defaultSeconds = defSeconds;

        if (_defaultSeconds < 1) {
            _defaultSeconds = 30;
        }

        try {
            if (StrUtils.isEmpty(user) || StrUtils.isEmpty(password)) {
                _cache = new MemcachedClient(new ConnectionFactoryBuilder()
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).build(),
                        AddrUtil.getAddresses(server));
            } else {
                AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                        new PlainCallbackHandler(user, password));

                _cache = new MemcachedClient(new ConnectionFactoryBuilder()
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                        .setAuthDescriptor(ad).build(),
                        AddrUtil.getAddresses(server));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void store(String key, Object obj, int seconds) {
        if (_cache != null) {
            String newKey = newKey(key);
            try {
                if (seconds > 0) {
                    _cache.set(newKey, seconds, obj);
                } else {
                    _cache.set(newKey, getDefalutSeconds(), obj);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public <T> T get(String key, Type type) {
        if (_cache != null) {
            String newKey = newKey(key);
            try {
                return (T)_cache.get(newKey);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void remove(String key) {
        if (_cache != null) {
            String newKey = newKey(key);
            _cache.delete(newKey);
        }
    }

    @Override
    public int getDefalutSeconds() {
        return _defaultSeconds;
    }

    @Override
    public String getCacheKeyHead() {
        return _cacheKeyHead;
    }

    private String newKey(String key) {
        return _cacheKeyHead + "$" + EncryptUtils.md5(key);
    }
}
