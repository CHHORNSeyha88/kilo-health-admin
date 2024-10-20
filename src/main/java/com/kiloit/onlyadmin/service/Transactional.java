package com.kiloit.onlyadmin.service;

public @interface Transactional {

    boolean readOnly();
}
