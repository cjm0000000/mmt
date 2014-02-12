package com.github.cjm0000000.mmt.shared;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * MMT context, it's a spring context
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Component
public final class MMTContext implements ApplicationContextAware {
  @Autowired
  private ApplicationContext context;

  private MMTContext() {}

  public ApplicationContext getApplicationContext() {
    return context;
  }

  @Override
  public synchronized void setApplicationContext(ApplicationContext ctx) throws BeansException {
    context = ctx;
  }
}
