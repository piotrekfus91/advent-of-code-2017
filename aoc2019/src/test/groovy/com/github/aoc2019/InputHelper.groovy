package com.github.aoc2019

import groovy.transform.CompileStatic

@CompileStatic
trait InputHelper {
    String getTaskInput() {
        String fileName = "${this.class.simpleName.toLowerCase() - 'test'}.txt"
        return this.class.classLoader.getResource(fileName).text
    }
}
