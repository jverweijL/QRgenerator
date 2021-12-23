package com.liferay.demo.qr.portlet;

import com.liferay.demo.qr.constants.QRCodeGeneratorPortletKeys;

import com.liferay.demo.qr.portlet.configuration.QRCodeGeneratorPortletConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.*;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.awt.image.RenderedImage;
import java.io.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.osgi.service.component.annotations.Modified;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author jverweij
 */
@Component(
	configurationPid = "com.liferay.demo.qr.portlet.configuration.QRCodeGeneratorPortletConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + QRCodeGeneratorPortletKeys.QRCodeGenerator,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class QRCodeGeneratorPortlet extends MVCPortlet {

	private volatile QRCodeGeneratorPortletConfiguration _configuration;
	private static final Log _log = LogFactoryUtil.getLog(QRCodeGeneratorPortlet.class);


	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws  IOException,PortletException {

		_log.debug("Creating QR Code.");
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(renderRequest);

		String myCodeText = "";
		String foreground = "";
		String background = "";

		PortletPreferences preferences = renderRequest.getPreferences();
		if (Validator.isNotNull(preferences))
		{
			foreground = GetterUtil.getString(preferences.getValue("foregroundColor",QRCodeGeneratorPortletKeys.FOREGROUND));
			background = GetterUtil.getString(preferences.getValue("backgroundColor",QRCodeGeneratorPortletKeys.BACKGROUND));

			myCodeText = GetterUtil.getString(preferences.getValue("url",""));
		}

		if (foreground.isEmpty()) foreground = QRCodeGeneratorPortletKeys.FOREGROUND;
		if (background.isEmpty()) background = QRCodeGeneratorPortletKeys.BACKGROUND;
		if (myCodeText.isEmpty()) myCodeText = PortalUtil.getCurrentCompleteURL(httpRequest);

		// replace the matches
		myCodeText = myCodeText.replaceAll(GetterUtil.getString(preferences.getValue("match","")),GetterUtil.getString(preferences.getValue("replace","")));

		int size = 250;
		String fileType = "png";
		try {
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); // default = 4 //
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.decode(background));
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.decode(foreground));

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			String base64String = imgToBase64String(image, fileType);

			renderRequest.setAttribute("QRCode","data:image/png;base64," + base64String);

		} catch (WriterException e) {
			e.printStackTrace();
		}
		//renderRequest.setAttribute("QRCode","abc");
		_log.debug("\n\nYou have successfully created QR Code.");
		super.doView(renderRequest, renderResponse);
	}

	public static String imgToBase64String(final RenderedImage img, final String formatName) {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
			return os.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String,Object> properties)
	{
		_configuration = ConfigurableUtil.createConfigurable(QRCodeGeneratorPortletConfiguration.class,properties);
	}
}