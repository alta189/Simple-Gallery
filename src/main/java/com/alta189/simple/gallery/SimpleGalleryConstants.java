package com.alta189.simple.gallery;

import com.alta189.simple.gallery.objects.Result;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class SimpleGalleryConstants {
    public static class Resources {
        public static final String ZURB_FOUNDATION_5_2_2_DOWNLOAD = "http://foundation.zurb.com/cdn/releases/foundation-5.5.2.zip";
        public static final String ZURB_FOUNDATION_5_2_2_SHA1 = "79F38106D2F37F1CE617AAD0823E2DFCEE0AA202";
        public static final String RESOURCES_SHA1_MAP = "{\"css\":{\"foundation.min.css\":\"c6c7de34326465934c34ebaf7fc513bc6006508f\",\"normalize.css\":\"e58275a588bb631a37a2988145eea231ed23176b\",\"foundation.css\":\"94176efdb3717b73e18654e963c2ade60c15bea2\"},\"img\":{\".gitkeep\":\"adc83b19e793491b1c6ea0fd8b46cd9f32e592fc\"},\"js\":{\"vendor\":{\"jquery.js\":\"2ada42c8fd78888a0d0c8ec0dd08e58fe136b0d2\",\"placeholder.js\":\"cfb2f3e99153b1f148794ced232d382f968b87c9\",\"fastclick.js\":\"38536a885bc90416c582a3c4eac92c179355f6f8\",\"jquery.cookie.js\":\"74f7f68550e90a1def6faa6de6e0830f1ab34970\",\"modernizr.js\":\"b6a3f1e66ef1376216117f3c2fc71d735bf6eb5a\"},\"foundation.min.js\":\"39d4fc810b8c8a6a1824e5bfcdd08a921ca01d8e\",\"foundation\":{\"foundation.reveal.js\":\"b81c6f665f26f6619d37d3a3d325f5244f3b6172\",\"foundation.dropdown.js\":\"28bf5b41347df24e605ded2e33da3e7dc5ac8071\",\"foundation.abide.js\":\"2f27efa14226107231cd03509e58f7d8880d5bb8\",\"foundation.slider.js\":\"340b971c4fe801d38db79f06d99ad0e1113d82c0\",\"foundation.alert.js\":\"5bb7a0e3b89d546f690bd9776907bf2fb6cab0b3\",\"foundation.clearing.js\":\"704bee2435099cd88929af413aff2263a98500ae\",\"foundation.js\":\"7ab5be32a0c0e876a609d38f6858063860b1d9f1\",\"foundation.joyride.js\":\"7bd6dbb5693bd8f577e179fba8a9d9a7ce9e5d30\",\"foundation.equalizer.js\":\"20feb67892a8169a5b685b305245c022fc44b475\",\"foundation.accordion.js\":\"c9641dc54bfb60080f7e4138d8f441a80b075c2d\",\"foundation.magellan.js\":\"000ae1c93144069ee0e6d50f09e14c980f7a3f0b\",\"foundation.orbit.js\":\"f34cba97af304a1df4abe293c0286cf0214fe1ca\",\"foundation.topbar.js\":\"40a4534c9dfb686d0ee42a77595670459226c6d5\",\"foundation.interchange.js\":\"4899aec66f2e2e8ff7a5cc3e30cc4bcb23866ed8\",\"foundation.offcanvas.js\":\"23f0886861b38224f6b4df98f595c13baf7f864b\",\"foundation.tab.js\":\"70e5faa6a3b6a55d4500d575c3070e92d1edf0e7\",\"foundation.tooltip.js\":\"90fdda89d7193c9652fc48120c8e5d7f363829d0\"}}}";
    }

    public static class GsonTypes {
        public static final Type TYPE_MAP_STRING_OBJECT = new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    public static class Results {
	    public static final Result SUCCESS = Result.wrap("success");
    }
}
