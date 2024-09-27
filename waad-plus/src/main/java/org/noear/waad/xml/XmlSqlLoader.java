package org.noear.waad.xml;

import org.noear.waad.WoodException;
import org.noear.waad.utils.IOUtils;
import org.noear.waad.utils.ThrowableUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class XmlSqlLoader {
    public static XmlSqlLoader _g = new XmlSqlLoader();

    private static final ReentrantLock SYNC_LOCK = new ReentrantLock();

    private boolean is_loaed = false;
    private List<URL> xmlFiles = new ArrayList<>();

    /**
     * 加载扩展文件夹（或文件）
     */
    public static void load() throws Exception {
        if (_g.is_loaed == false) {
            SYNC_LOCK.tryLock();

            try {
                if (_g.is_loaed == false) {
                    _g.is_loaed = true;

                    _g.load0();
                }
            }finally {
                SYNC_LOCK.unlock();
            }
        }
    }

    public static void tryLoad() {
        try {
            load();
        } catch (Throwable ex) {
            ex = ThrowableUtils.throwableUnwrap(ex);
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }
    }

    private void load0() throws Exception {
        XmlFileScaner.scan("waad/", ".xml")
                .stream()
                .map(k -> IOUtils.getResource(k))
                .filter(url -> url != null)
                .forEach(url -> _g.xmlFiles.add(url));

        if (_g.xmlFiles.size() == 0) {
            return;
        }

        //构建代码
        List<String> codes = new ArrayList<>();
        for (URL file : _g.xmlFiles) {
            System.out.println("[Wood] Xml Compiler: " + file);

            String code = XmlSqlCompiler.parse(file);

            if (code != null) {
                codes.add(code);
            }
        }

        if (codes.size() == 0) {
            return;
        }

        boolean is_ok = CompilerUtil.instance().compiler(codes);
        if (is_ok) {
            CompilerUtil.instance().loadClassAll(true);
        } else {
            String error = CompilerUtil.instance().getCompilerMessage();
            throw new WoodException("Xml sql compiler error: \r\n" + error);
        }
    }


    private XmlSqlLoader() {
    }
}
