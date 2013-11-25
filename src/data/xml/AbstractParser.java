package data.xml;

import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import helpers.ErrorHandlerImpl;
import helpers.LoggerObject;
import helpers.SimpleDTDEntityResolver;
import helpers.configuration.PropertyTransformerFactory;
import helpers.configuration.XmlAttribute;
import helpers.configuration.transformers.PropertyTransformer;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:42
 */
public abstract class AbstractParser<H extends AbstractHolder> extends LoggerObject {
    protected final H _holder;

    protected String _currentFile;
    protected SAXReader _reader;

    protected AbstractParser(H holder) {
        _holder = holder;
        _reader = new SAXReader();
        _reader.setValidation(true);
        _reader.setErrorHandler(new ErrorHandlerImpl(this));
    }

    protected void initDTD(File f) {
        _reader.setEntityResolver(new SimpleDTDEntityResolver(f));
    }

    protected void parseDocument(InputStream f, String name) throws Exception {
        _currentFile = name;

        org.dom4j.Document document = _reader.read(f);

        readData(document.getRootElement());
    }

    protected abstract void readData(Element rootElement) throws Exception;

    protected abstract void parse();

    protected H getHolder() {
        return _holder;
    }

    public String getCurrentFileName() {
        return _currentFile;
    }

    public void load() {
        parse();
        _holder.process();
        _holder.log();
    }

    public void reload() {
        _reader = new SAXReader();
        _reader.setValidation(true);
        _reader.setErrorHandler(new ErrorHandlerImpl(this));

        info("reload start...");
        _holder.clear();
        load();
    }

    public void clearReader() {
        _reader = null;
    }

    public void processFields(Object object, Attribute attr) throws ClassNotFoundException {
        Class<?> clazz;

        if (object instanceof Class) {
            clazz = (Class<?>) object;
        } else {
            clazz = object.getClass();
        }

        List<Class> classz = new ArrayList<Class>();
        Class cd = clazz;
        while (true) {
            classz.add(cd);
            Class c = cd.getSuperclass();
            if (c == null) {
                break;
            }
            cd = c;
        }

        for (Class cs : classz)
            for (Field f : cs.getDeclaredFields()) {
                if (f == null)
                    continue;
                try {
                    XmlAttribute property = f.getAnnotation(XmlAttribute.class);
                    if (property == null) {
                        continue;
                    }

                    if ((!property.name().isEmpty() && property.name().equals(attr.getName())) || f.getName().equals(attr.getName())) {
                        boolean oldAccessible = f.isAccessible();
                        f.setAccessible(true);
                        f.set(object, getFieldValue(f, attr.getValue()));
                        f.setAccessible(oldAccessible);
                        break;
                    }
                } catch (Exception ex) {
                    error("Can't transform field " + f.getName() + " [ " + attr.getValue() + " ] of class " + f.getDeclaringClass());
                    throw new RuntimeException();
                }
            }
    }

    public void processElementFields(Object object, Field f, Element el) throws ClassNotFoundException {
        try {
            XmlAttribute property = f.getAnnotation(XmlAttribute.class);
            if (property == null) {
                return;
            }

            if ((!property.name().isEmpty() && property.name().equals(el.getName())) || f.getName().equals(el.getName())) {
                boolean oldAccessible = f.isAccessible();
                f.setAccessible(true);

                Object[] objs = (Object[]) f.get(object);
                if (objs == null) {
                    objs = new Object[0];
                }
                objs = ArrayUtils.add(objs, getFieldValue(f, el.getText()));
                f.set(object, objs);
                f.setAccessible(oldAccessible);
            }
        } catch (Exception ex) {
            error("Can't transform field " + f.getName() + " [ " + el.getText() + " ] of class " + f.getDeclaringClass());
            throw new RuntimeException();
        }
    }

    public void addArrayElementField(Object object, Field f, String name, Object elem) throws ClassNotFoundException {
        try {
            XmlAttribute property = f.getAnnotation(XmlAttribute.class);
            if (property == null) {
                return;
            }

            if ((!property.name().isEmpty() && property.name().equals(name)) || f.getName().equals(name)) {
                boolean oldAccessible = f.isAccessible();
                f.setAccessible(true);
                Object[] objs = (Object[]) f.get(object);
                if (objs == null) {
                    objs = new Object[0];
                }
                objs = ArrayUtils.add(objs, elem);
                f.set(object, objs);
                f.setAccessible(oldAccessible);
            }
        } catch (Exception ex) {
            error("Can't transform field " + f.getName() + " [ " + name + " ] of class " + f.getDeclaringClass());
            throw new RuntimeException();
        }
    }

    public Object setClassAndGetField(Object object, String name) throws ClassNotFoundException {
        Class<?> clazz;
        if (object instanceof Class) {
            clazz = (Class<?>) object;
        } else {
            clazz = object.getClass();
        }

        Object obj = null;

        List<Class> classz = new ArrayList<Class>();
        Class cd = clazz;
        while (true) {
            classz.add(cd);
            Class c = cd.getSuperclass();
            if (c == null) {
                break;
            }
            cd = c;
        }

        for (Class cs : classz)
            for (Field f : cs.getDeclaredFields()) {
                if (f == null)
                    continue;
                try {
                    XmlAttribute property = f.getAnnotation(XmlAttribute.class);
                    if (property == null)
                        continue;

                    if ((!property.name().isEmpty() && property.name().equals(name)) || f.getName().equals(name)) {
                        boolean oldAccessible = f.isAccessible();
                        f.setAccessible(true);
                        obj = f.getType().newInstance();
                        f.set(object, obj);
                        f.setAccessible(oldAccessible);
                        break;
                    }

                } catch (Exception ex) {
                    error("Can't transform field " + f.getName() + " [ " + name + " ] of class " + f.getDeclaringClass());
                    throw new RuntimeException();
                }
            }
        return obj;
    }

    public void setClassField(Object object, Object param, String name) throws ClassNotFoundException {
        Class<?> clazz;
        if (object instanceof Class) {
            clazz = (Class<?>) object;
        } else {
            clazz = object.getClass();
        }

        List<Class> classz = new ArrayList<Class>();
        Class cd = clazz;
        while (true) {
            classz.add(cd);
            Class c = cd.getSuperclass();
            if (c == null) {
                break;
            }
            cd = c;
        }

        for (Class cs : classz)
            for (Field f : cs.getDeclaredFields()) {
                if (f == null)
                    continue;
                try {
                    XmlAttribute property = f.getAnnotation(XmlAttribute.class);
                    if (property == null)
                        continue;

                    if ((!property.name().isEmpty() && property.name().equals(name)) || f.getName().equals(name)) {
                        boolean oldAccessible = f.isAccessible();
                        f.setAccessible(true);
                        f.set(object, param);
                        f.setAccessible(oldAccessible);
                        break;
                    }

                } catch (Exception ex) {
                    error("Can't transform field " + f.getName() + " [ " + name + " ] of class " + f.getDeclaringClass());
                    throw new RuntimeException();
                }
            }
    }

    public void processAttr(Iterator<Attribute> attrs, Object obj) throws Exception {
        while (attrs.hasNext()) {
            Attribute attr = attrs.next();
            processFields(obj, attr);
        }
    }

    public Field getField(Object object, String name) throws ClassNotFoundException {
        Class<?> clazz;
        if (object instanceof Class) {
            clazz = (Class<?>) object;
        } else {
            clazz = object.getClass();
        }

        Field fo = null;

        List<Class> classz = new ArrayList<Class>();
        Class cd = clazz;
        while (true) {
            classz.add(cd);
            Class c = cd.getSuperclass();
            if (c == null) {
                break;
            }
            cd = c;
        }

        for (Class cs : classz)
            for (Field f : cs.getDeclaredFields()) {
                if (f == null)
                    continue;
                try {
                    XmlAttribute property = f.getAnnotation(XmlAttribute.class);
                    if (property == null)
                        continue;

                    if ((!property.name().isEmpty() && property.name().equals(name)) || f.getName().equals(name)) {
                        fo = f;
                        break;
                    }

                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        return fo;
    }

    public Object getFieldValue(Field field, String value) throws TransformationException {
        XmlAttribute property = field.getAnnotation(XmlAttribute.class);
        Class clz = field.getType().isArray() ? field.getType().getComponentType() : field.getType();
        PropertyTransformer<?> pt = PropertyTransformerFactory.newTransformer(clz, property.propertyTransformer());

        return pt.transform(value, field);
    }

    public Object newInstanceOfType(Class<?> clazz) {
        Object cls = null;
        try {
            cls = clazz.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return cls;
    }

    public Object newInstanceOfType(Class<?> clazz, Class<?>[] classParams, Object... params) {
        Object cls = null;
        try {
            Constructor c = clazz.getDeclaredConstructor(classParams);
            cls = c.newInstance(params);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cls;
    }

    public Object newInstanceOfType(XmlElement[] xmls, String name) {
        Object cls = null;
        try {
            for (XmlElement xml : xmls) {
                if (xml.name().equals(name)) {
                    cls = xml.type().newInstance();
                    break;
                }
            }
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return cls;
    }
}
