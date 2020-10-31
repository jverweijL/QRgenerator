package com.liferay.demo.qr.portlet.configuration.definition;

import com.liferay.demo.qr.portlet.configuration.QRCodeGeneratorPortletConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

public class QRCodeGeneratorPortletConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {
    @Override
    public Class<?> getConfigurationBeanClass()
    {
        return QRCodeGeneratorPortletConfiguration.class;
    }
}
