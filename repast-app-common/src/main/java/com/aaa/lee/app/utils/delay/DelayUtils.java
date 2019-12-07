package com.aaa.lee.app.utils.delay;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DelayUtils implements BeanFactoryAware {

        //Bean工厂必须是static类型，否则系统启动的时候将无法写入factory
        private static BeanFactory factory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            factory = beanFactory;
        }

        public static Object getBean(String beanName){
            if(StringUtils.isEmpty(beanName)){
                return null;
            }
            Object t= factory.getBean(beanName);
            return t;
        }
}
