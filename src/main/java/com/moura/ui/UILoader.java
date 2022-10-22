package com.moura.ui;

import java.net.URL;

public class UILoader {
    
    public static URL load(String resourceName) {
        return UILoader.class.getResource(resourceName);
    }
}
