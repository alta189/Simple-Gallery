package com.alta189.simple.gallery;

import com.alta189.simple.gallery.objects.Result;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class SimpleGalleryConstants {
    public static class Resources {
        public static final String ZURB_FOUNDATION_5_2_2_DOWNLOAD = "http://foundation.zurb.com/cdn/releases/foundation-5.5.2.zip";
        public static final String ZURB_FOUNDATION_5_2_2_SHA1 = "79F38106D2F37F1CE617AAD0823E2DFCEE0AA202";
        public static final String RESOURCES_SHA1_MAP = "{\"css\":{\"foundation.min.css\":\"C6C7DE34326465934C34EBAF7FC513BC6006508F\",\"normalize.css\":\"E58275A588BB631A37A2988145EEA231ED23176B\",\"foundation.css\":\"94176EFDB3717B73E18654E963C2ADE60C15BEA2\"},\"img\":{\".gitkeep\":\"ADC83B19E793491B1C6EA0FD8B46CD9F32E592FC\"},\"js\":{\"vendor\":{\"jquery.js\":\"2ADA42C8FD78888A0D0C8EC0DD08E58FE136B0D2\",\"placeholder.js\":\"CFB2F3E99153B1F148794CED232D382F968B87C9\",\"fastclick.js\":\"38536A885BC90416C582A3C4EAC92C179355F6F8\",\"jquery.cookie.js\":\"74F7F68550E90A1DEF6FAA6DE6E0830F1AB34970\",\"modernizr.js\":\"B6A3F1E66EF1376216117F3C2FC71D735BF6EB5A\"},\"foundation.min.js\":\"39D4FC810B8C8A6A1824E5BFCDD08A921CA01D8E\",\"foundation\":{\"foundation.reveal.js\":\"B81C6F665F26F6619D37D3A3D325F5244F3B6172\",\"foundation.dropdown.js\":\"28BF5B41347DF24E605DED2E33DA3E7DC5AC8071\",\"foundation.abide.js\":\"2F27EFA14226107231CD03509E58F7D8880D5BB8\",\"foundation.slider.js\":\"340B971C4FE801D38DB79F06D99AD0E1113D82C0\",\"foundation.alert.js\":\"5BB7A0E3B89D546F690BD9776907BF2FB6CAB0B3\",\"foundation.clearing.js\":\"704BEE2435099CD88929AF413AFF2263A98500AE\",\"foundation.js\":\"7AB5BE32A0C0E876A609D38F6858063860B1D9F1\",\"foundation.joyride.js\":\"7BD6DBB5693BD8F577E179FBA8A9D9A7CE9E5D30\",\"foundation.equalizer.js\":\"20FEB67892A8169A5B685B305245C022FC44B475\",\"foundation.accordion.js\":\"C9641DC54BFB60080F7E4138D8F441A80B075C2D\",\"foundation.magellan.js\":\"000AE1C93144069EE0E6D50F09E14C980F7A3F0B\",\"foundation.orbit.js\":\"F34CBA97AF304A1DF4ABE293C0286CF0214FE1CA\",\"foundation.topbar.js\":\"40A4534C9DFB686D0EE42A77595670459226C6D5\",\"foundation.interchange.js\":\"4899AEC66F2E2E8FF7A5CC3E30CC4BCB23866ED8\",\"foundation.offcanvas.js\":\"23F0886861B38224F6B4DF98F595C13BAF7F864B\",\"foundation.tab.js\":\"70E5FAA6A3B6A55D4500D575C3070E92D1EDF0E7\",\"foundation.tooltip.js\":\"90FDDA89D7193C9652FC48120C8E5D7F363829D0\"}}}\n";
    }

    public static class GsonTypes {
        public static final Type TYPE_MAP_STRING_OBJECT = new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    public static class Results {
	    public static final Result SUCCESS = Result.wrap("success");
    }
}
