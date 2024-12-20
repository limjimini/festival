package greenart.factory;

import org.springframework.beans.factory.BeanCreationException;

public class BeanCreationExceptionImpl extends BeanCreationException {
    public BeanCreationExceptionImpl(String msg) {
        super(msg);
    }

    public BeanCreationExceptionImpl(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanCreationExceptionImpl(String beanName, String msg) {
        super(beanName, msg);
    }

    public BeanCreationExceptionImpl(String beanName, String msg, Throwable cause) {
        super(beanName, msg, cause);
    }

    public BeanCreationExceptionImpl(String resourceDescription, String beanName, String msg) {
        super(resourceDescription, beanName, msg);
    }

    public BeanCreationExceptionImpl(String resourceDescription, String beanName, String msg, Throwable cause) {
        super(resourceDescription, beanName, msg, cause);
    }
}
