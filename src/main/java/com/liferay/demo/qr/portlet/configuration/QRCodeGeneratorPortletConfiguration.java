package com.liferay.demo.qr.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = "liferay-custom",
        scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
        id = "com.liferay.demo.qr.portlet.configuration.QRCodeGeneratorPortletConfiguration"
)
public interface QRCodeGeneratorPortletConfiguration {
    @Meta.AD(deflt = "", required = false)
    String url();

    @Meta.AD(deflt = "", required = false)
    String match();

    @Meta.AD(deflt = "", required = false)
    String replace();

    @Meta.AD(deflt = "#000000", required = false)
    String foregroundColor();

    @Meta.AD(deflt = "#FFFFFF", required = false)
    String backgroundColor();
}
