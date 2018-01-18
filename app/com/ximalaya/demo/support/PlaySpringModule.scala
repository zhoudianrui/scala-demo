package com.ximalaya.demo.support

import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.guice.module.SpringModule


class PlaySpringModule extends SpringModule(new ClassPathXmlApplicationContext("application-context.xml")
) {
}
